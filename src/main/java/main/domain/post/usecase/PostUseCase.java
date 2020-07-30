package main.domain.post.usecase;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostSaveRequestDTO;
import main.domain.post.model.PostSaveResponseDTO;
import main.domain.post.model.PostResponseDTO;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.model.CommentResponseDTO;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import main.domain.tag.entity.Tag;
import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostUseCase {
    private PostRepositoryPort postRepositoryPort;
    private UserRepositoryPort userRepositoryPort;
    private PostCommentsRepositoryPort postCommentsRepositoryPort;

    @Autowired
    public PostUseCase(PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort, PostCommentsRepositoryPort postCommentsRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.postCommentsRepositoryPort = postCommentsRepositoryPort;;
    }


    public PostResponseDTO getPosts(int offset, int limit, String mode) {
        List<Post> posts = postRepositoryPort.getAllPosts(mode);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostResponseDTO searchPosts(int offset, int limit, String query) {
        List<Post> posts = postRepositoryPort.searchPosts(query);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostResponseDTO getPostsByDate(int offset, int limit, String date) {
        List<Post> posts = postRepositoryPort.getPostsByDate(date);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostInfoDTO getPostById(Integer id){
        //Поиск поста в БД по id
        Post post = postRepositoryPort.getActivePostById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        //Создание DTO из поста
        PostInfoDTO postInfoDTO = postToDTO(post);

        //Получение комментариев к посту
        List<PostComment> comments = postCommentsRepositoryPort.getCommentsByPostId(post.getId());

        //Создание списка DTO комментариев
        List<Object> commentsDTO = new ArrayList<>();
        comments.forEach(postComment -> {
            User user = userRepositoryPort.findById(postComment.getUserId()).orElseThrow();
            CommentResponseDTO postCommentDTO = new CommentResponseDTO(
                    postComment.getId(),
                    postComment.getTime(),
                    postComment.getText(),
                    user.getId(),
                    user.getName(),
                    user.getPhoto()
            );
            commentsDTO.add(postCommentDTO);
        });

        postInfoDTO.setComments(commentsDTO);   //Добавление списка DTO комментариев к DTO поста
        List<Tag> tags = post.getTags();    //Получение тэгов к посту
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        postInfoDTO.setTags(tagNames);  //Добавление тэгов к DTO поста

        return postInfoDTO;
    }

    public PostResponseDTO getPostsByTag(int offset, int limit, String tag) {
        List<Post> posts = postRepositoryPort.getPostsByTag(tag);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostResponseDTO getPostsModeration(int offset, int limit, String status) {
        List<Post> posts;
        switch (status){
            case "new":
                posts = postRepositoryPort.getActivePostsByModerationStatus(ModerationStatus.NEW, null);
                break;
            case "declined":
                posts = postRepositoryPort.getActivePostsByModerationStatus(ModerationStatus.DECLINED, getCurrentUser().getId());
                break;
            case "accepted":
                posts = postRepositoryPort.getActivePostsByModerationStatus(ModerationStatus.ACCEPTED, getCurrentUser().getId());
                break;
            default:
                throw new IllegalArgumentException();
        }
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostResponseDTO getCurrentUserPosts(int offset, int limit, String status) {
        List<Post> posts = postRepositoryPort.getCurrentUserPosts(getCurrentUser().getId(), status);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);
        return new PostResponseDTO(count, postInfoDTOList);
    }

    public PostSaveResponseDTO addPost(PostSaveRequestDTO requestDTO){
        //Поиск ошибок
        Map<String,String> errors = new LinkedHashMap<>();
        if (requestDTO.getTitle().trim().length() < 3){
            errors.put("title", "Заголовок не установлен");
        }
        if (requestDTO.getText().trim().replaceAll("<[^<>]+>", "").length() < 50){
            errors.put("text", "Текст публикации слишком короткий");
        }
        if (errors.size() > 0)
            return new PostSaveResponseDTO(errors);

        //Маппинг тэгов из запроса
        List<Tag> tagList = Arrays.stream(requestDTO.getTags())
                .map(Tag::new)
                .collect(Collectors.toList());
        //Добавление поста
        Post post = new Post();
        post.setUserId(getCurrentUser().getId());
        post.setActive(requestDTO.isActive());
        post.setModerationStatus(ModerationStatus.NEW);
        Timestamp time = requestDTO.getTime().getTime() > System.currentTimeMillis() ?
                requestDTO.getTime() : new Timestamp(System.currentTimeMillis());
        post.setTime(time);
        post.setTitle(requestDTO.getTitle());
        post.setText(requestDTO.getText());
        post.setViewCount(0);
        post.setTags(tagList);
        postRepositoryPort.save(post);
        return new PostSaveResponseDTO(true);
    }

    public PostSaveResponseDTO editPost(Integer id, PostSaveRequestDTO requestDTO) {
        Post post = postRepositoryPort.findPostById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(getCurrentUser().getId() != post.getUserId()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Поиск ошибок
        Map<String,String> errors = new LinkedHashMap<>();
        if (requestDTO.getTitle().trim().length() < 3){
            errors.put("title", "Заголовок не установлен");
        }
        if (requestDTO.getText().trim().replaceAll("<[^<>]+>", "").length() < 50){
            errors.put("text", "Текст публикации слишком короткий");
        }
        if (errors.size() > 0)
            return new PostSaveResponseDTO(errors);

        //Маппинг тэгов из запроса
        List<Tag> tagList = Arrays.stream(requestDTO.getTags())
                .map(Tag::new)
                .collect(Collectors.toList());

        //Редактирование и сохранение поста
        post.setActive(requestDTO.isActive());
        post.setModerationStatus(ModerationStatus.NEW);
        Timestamp time = requestDTO.getTime().getTime() > System.currentTimeMillis() ?
                requestDTO.getTime() : new Timestamp(System.currentTimeMillis());
        post.setTime(time);
        post.setTitle(requestDTO.getTitle());
        post.setText(requestDTO.getText());
        post.setTags(tagList);
        postRepositoryPort.save(post);
        return new PostSaveResponseDTO(true);
    }

    //Получение списка с отступом и лимитом
    private List<Post> getWithOffsetAndLimit(List<Post> posts, int offset, int limit){
        return posts.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    //Создание списка с DTO постов
    private List<PostInfoDTO> postsListToDTO(List<Post> posts) {
        List<PostInfoDTO> listDTO = new ArrayList<>();
        posts.forEach(post -> listDTO.add(postToDTO(post)));
        return listDTO;
    }

    //Создание DTO из поста
    private PostInfoDTO postToDTO(Post post){
        Integer postId = post.getId();
        String time = postTimeToString(post.getTime());
        User user = userRepositoryPort.findById(post.getUserId()).orElseThrow();
        Integer userId = user.getId();
        String userName = user.getName();
        String title = post.getTitle();
        String announce = post.getText();
        Integer likeCount = post.getLikes().size();
        Integer dislikeCount = post.getDislikes().size();
        Integer commentCount = post.getComments().size();
        Integer viewCount = post.getViewCount();

        return new PostInfoDTO(
                postId,
                time,
                userId,
                userName,
                title,
                announce,
                likeCount,
                dislikeCount,
                commentCount,
                viewCount
        );
    }

    private String postTimeToString(Timestamp postTime){
        LocalDateTime postDateTime = postTime.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        int daysAgo = now.getDayOfYear() - postDateTime.getDayOfYear();

        String date;
        switch (daysAgo) {
            case 0:
                date = "Сегодня";
                break;
            case 1:
                date = "Вчера";
                break;
            default:
                date = postDateTime.format(DateTimeFormatter.ISO_DATE);
        }
        int hour = postDateTime.getHour();
        int minute = postDateTime.getMinute();
        String time = String.format("%s, %02d:%02d", date, hour, minute);
        return time;
    }

    private User getCurrentUser(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepositoryPort.findUserByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("User Not Authorized"));
    }

}
