package main.domain.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//Информация о посте
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostInfoDTO {
    private Integer id;
    private long timestamp;
    private Boolean active;
    private UserInfo user;
    private String title;
    private String announce;
    private String text;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;
    private List<Object> comments;
    private List<String> tags;

    public PostInfoDTO(Integer id, Timestamp timestamp, int userId, String userName, String title, String announce,
                       Integer likeCount, Integer dislikeCount, Integer commentCount, Integer viewCount) {
        this.id = id;   //post-id
        this.timestamp = timestamp.getTime()/1000;   //post-time
        this.user = new UserInfo(userId, userName);   //user-id,name
        this.title = title;     //post-title
        this.announce = announce;   //post-text
        this.likeCount = likeCount;     //post_votes-value(1)(count)
        this.dislikeCount = dislikeCount;   //post_votes-value(-1)(count)
        this.commentCount = commentCount;   //post_comments(count)
        this.viewCount = viewCount;     //post-view_count
    }

    //Информация о юзере
    private class UserInfo {
        Integer id;
        String name;

        public UserInfo(int userId, String userName) {
            this.id = userId;
            this.name = userName;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
