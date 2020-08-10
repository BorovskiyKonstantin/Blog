package main.domain.post.usecase;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.model.*;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.model.CommentResponseDTO;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import main.domain.tag.entity.Tag;
import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostUseCase {
    private PostRepositoryPort postRepositoryPort;
    private UserRepositoryPort userRepositoryPort;
    private PostCommentsRepositoryPort postCommentsRepositoryPort;
    private UserUseCase userUseCase;

    @Autowired
    public PostUseCase(PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort, PostCommentsRepositoryPort postCommentsRepositoryPort, UserUseCase userUseCase) {
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.postCommentsRepositoryPort = postCommentsRepositoryPort;
        this.userUseCase = userUseCase;
    }

    public PostResponseDTO getPosts(int offset, int limit, String mode) {
        //TODO сделать пагинацию либо заменить на 2 запроса в БД
        List<Post> posts = postRepositoryPort.getAllPosts(mode);
        int count = posts.size();
        posts = getWithOffsetAndLimit(posts, offset, limit);
        //todo==============================================
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
                .orElseThrow();

        //Получение DTO комментариев к посту
        List<PostComment> comments = postCommentsRepositoryPort.getCommentsByPostId(post.getId());
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

        //Получение DTO тэгов к посту
        List<Tag> tags = post.getTags();
        List<String> tagsDTO = tags.stream().map(Tag::getName).collect(Collectors.toList());

        //Создание DTO из поста
        PostInfoDTO postInfoDTO = postToDTO(post);
        postInfoDTO.setComments(commentsDTO);   //Добавление списка DTO комментариев к DTO поста
        postInfoDTO.setTags(tagsDTO);  //Добавление тэгов к DTO поста

        /**
         * todo:
         *      эти строки добавлены в связи с изменением параметров запросов/ответов в фронте.
         *      Разделить postInfoDTO на 2 класса:
         *      1. PostDTO - для списка List<PostDTO> внутри PostResponseDTO
         *      2. PostInfoDTO - для тела ответа на запрос фронта
         */
        postInfoDTO.setActive(true);
        postInfoDTO.setText(postInfoDTO.getAnnounce());
        postInfoDTO.setAnnounce(null);
        postInfoDTO.setCommentCount(null);
        //todo ==============================================

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
        Timestamp time = post.getTime();
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

    public Object moderation(PostModerationDTO requestDTO) {
        //Проверка: является ли пользователь модератором?
        User currentUser = getCurrentUser();
        if(!currentUser.isModerator())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        int postId = requestDTO.getPostId();
        String decision = requestDTO.getDecision();

        ModerationStatus status;
        switch (decision){
            case "accept":
                status = ModerationStatus.ACCEPTED;
                break;
            case "decline":
                status = ModerationStatus.DECLINED;
                break;
            default:
                return Collections.singletonMap("result", false);
        }

        int updateResult = postRepositoryPort.setPostModeration(postId, status, currentUser.getId());
        if(updateResult == 0)
            return Collections.singletonMap("result", false);
        else
            return Collections.singletonMap("result", true);
    }

    private User getCurrentUser(){
        return userUseCase.getCurrentUser();
    }

}
