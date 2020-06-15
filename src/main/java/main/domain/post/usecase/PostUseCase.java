package main.domain.post.usecase;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostRequestDTO;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.model.PostCommentDTO;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import main.domain.postvote.port.PostVoteRepositoryPort;
import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostUseCase {
    private PostRepositoryPort postRepositoryPort;
    private UserRepositoryPort userRepositoryPort;
    private PostVoteRepositoryPort postVoteRepositoryPort;
    private PostCommentsRepositoryPort postCommentsRepositoryPort;
    private TagRepositoryPort tagRepositoryPort;

    @Autowired
    public PostUseCase(PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort, PostVoteRepositoryPort postVoteRepositoryPort, PostCommentsRepositoryPort postCommentsRepositoryPort, TagRepositoryPort tagRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.postVoteRepositoryPort = postVoteRepositoryPort;
        this.postCommentsRepositoryPort = postCommentsRepositoryPort;
        this.tagRepositoryPort = tagRepositoryPort;
    }


    public PostRequestDTO getPosts(int offset, int limit, String mode) {
        List<Post> posts = postRepositoryPort.getAllPosts(mode);
        int count = posts.size();
        //Offset and limit
        posts = getWithOffsetAndLimit(posts, offset, limit);

        //Список DTO c информацией постов
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);

        //Общий DTO
        return new PostRequestDTO(count, postInfoDTOList);
    }

    public PostRequestDTO searchPosts(int offset, int limit, String query) {
        List<Post> posts = postRepositoryPort.searchPosts(query);
        int count = posts.size();
        //Offset and limit
        posts = getWithOffsetAndLimit(posts, offset, limit);

        //Список DTO c информацией постов
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);

        //Общий DTO
        return new PostRequestDTO(count, postInfoDTOList);
    }

    public PostRequestDTO getPostsByDate(int offset, int limit, String date) {
        List<Post> posts = postRepositoryPort.getPostsByDate(date);
        int count = posts.size();
        //Offset and limit
        posts = getWithOffsetAndLimit(posts, offset, limit);

        //Список DTO c информацией постов
        List<PostInfoDTO> postInfoDTOList = postsListToDTO(posts);

        //Общий DTO
        return new PostRequestDTO(count, postInfoDTOList);
    }

    public PostInfoDTO getPostById(Integer id){
        //Поиск поста в БД по id
        Post post = postRepositoryPort.getPostById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        //Создание DTO из поста
        PostInfoDTO postInfoDTO = postToDTO(post);

        //Получение комментариев к посту
        List<PostComment> comments = postCommentsRepositoryPort.getCommentsByPostId(post.getId());

        //Создание DTO комментариев
        List<Object> commentsDTO = new ArrayList<>();
        comments.forEach(postComment -> {
            User user = userRepositoryPort.findById(postComment.getUserId()).orElseThrow();
            PostCommentDTO postCommentDTO = new PostCommentDTO(
                    postComment.getId(),
                    postComment.getTime(),
                    postComment.getText(),
                    user.getId(),
                    user.getName(),
                    user.getPhoto()
            );
            commentsDTO.add(postCommentDTO);
        });

        //Добавление комментариев к DTO поста
        postInfoDTO.setComments(commentsDTO);

        //Получение тэгов к посту
        List<Tag> tags = post.getTags();
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());

        //Добавление тэгов к DTO поста
        postInfoDTO.setTags(tagNames);

        return postInfoDTO;
    }


    //Получение списка с отступом и лимитом
    private List<Post> getWithOffsetAndLimit(List<Post> posts, int offset, int limit){
        if (offset > posts.size())
            return new ArrayList<>();

        int toIndex = Math.min((offset + limit), posts.size());
        List <Post> subList = posts.subList(offset, toIndex);
        return subList;
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
}
