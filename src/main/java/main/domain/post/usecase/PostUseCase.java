package main.domain.post.usecase;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostRequestDTO;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import main.domain.postvote.port.PostVoteRepositoryPort;
import main.domain.user.entity.User;
import main.domain.user.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostUseCase {
    private PostRepositoryPort postRepositoryPort;
    private UserRepositoryPort userRepositoryPort;
    private PostVoteRepositoryPort postVoteRepositoryPort;
    private PostCommentsRepositoryPort postCommentsRepositoryPort;

    @Autowired
    public PostUseCase(PostRepositoryPort postRepositoryPort, UserRepositoryPort userRepositoryPort, PostVoteRepositoryPort postVoteRepositoryPort, PostCommentsRepositoryPort postCommentsRepositoryPort) {
        this.postRepositoryPort = postRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.postVoteRepositoryPort = postVoteRepositoryPort;
        this.postCommentsRepositoryPort = postCommentsRepositoryPort;
    }

    public PostRequestDTO getPosts(int offset, int limit, String mode) {
        List<Post> posts = postRepositoryPort.getAllPosts(mode);
        int count = posts.size();
        //Offset and limit
        posts = getWithOffsetAndLimit(posts, offset, limit);

        //Список DTO c информацией постов
        List<PostInfoDTO> postInfoDTOList = new ArrayList<>();
        posts.forEach(post -> {
            Integer postId = post.getId();
            String time = postTimeToString(post.getTime());
            User user = userRepositoryPort.findById(post.getUserId()).orElseThrow();
            Integer userId = user.getId();
            String userName = user.getName();
            String title = post.getTitle();
            String announce = post.getText();
            Integer likeCount = post.getLikes().size();
            Integer dislikeCount = post.getDislikes().size();
            Integer commentCount = postCommentsRepositoryPort.getCommentCountByPostId(postId);
            Integer viewCount = post.getViewCount();
            //Заполнение DTO
            postInfoDTOList.add(
                    new PostInfoDTO(
                            postId,
                            time,
                            userId,
                            userName,
                            title,
                            announce,
                            likeCount,
                            dislikeCount,
                            commentCount,
                            viewCount));
        });
        //Общий DTO
        return new PostRequestDTO(count, postInfoDTOList);
    }

    public PostRequestDTO searchPosts(int offset, int limit, String query) {
        List<Post> posts = postRepositoryPort.searchPosts(query);
        int count = posts.size();
        //Offset and limit
        posts = getWithOffsetAndLimit(posts, offset, limit);

        //Список DTO c информацией постов
        List<PostInfoDTO> postInfoDTOList = new ArrayList<>();
        posts.forEach(post -> {
            Integer postId = post.getId();
            String time = postTimeToString(post.getTime());
            User user = userRepositoryPort.findById(post.getUserId()).orElseThrow();
            Integer userId = user.getId();
            String userName = user.getName();
            String title = post.getTitle();
            String announce = post.getText();
            Integer likeCount = postVoteRepositoryPort.getLikeCountByPostId(postId);
            Integer dislikeCount = postVoteRepositoryPort.getDislikeCountByPostId(postId);
            Integer commentCount = postCommentsRepositoryPort.getCommentCountByPostId(postId);
            Integer viewCount = post.getViewCount();
            //Заполнение DTO
            postInfoDTOList.add(
                    new PostInfoDTO(
                            postId,
                            time,
                            userId,
                            userName,
                            title,
                            announce,
                            likeCount,
                            dislikeCount,
                            commentCount,
                            viewCount));
        });
        //Общий DTO
        return new PostRequestDTO(count, postInfoDTOList);
    }

    private List<Post> getWithOffsetAndLimit(List<Post> posts, int offset, int limit){
        if (offset > posts.size())
            return new ArrayList<>();

        int toIndex = Math.min((offset + limit), posts.size());
        List <Post> subList = posts.subList(offset, toIndex);
        return subList;
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
