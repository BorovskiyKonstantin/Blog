package main.domain.post.usecase;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostsDTO;
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
    @Autowired
    PostRepositoryPort postRepositoryPort;

    @Autowired
    UserRepositoryPort userRepositoryPort;

    @Autowired
    PostVoteRepositoryPort postVoteRepositoryPort;

    @Autowired
    PostCommentsRepositoryPort postCommentsRepositoryPort;


    public PostsDTO getPostsDTO(int offset, int limit, String mode) {
        List<Post> posts = postRepositoryPort.getAllPosts(offset, limit, mode);
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
            postInfoDTOList.add(new PostInfoDTO(postId, time, userId, userName, title, announce, likeCount, dislikeCount, commentCount, viewCount));
        });
        //Общий DTO
        return new PostsDTO(postRepositoryPort.count(), postInfoDTOList);
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
