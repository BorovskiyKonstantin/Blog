package main.domain.post.model;

public class PostInfoDTO {
    Integer id;
    String time;
    UserInfo user;
    String title;
    String announce;
    Integer likeCount;
    Integer dislikeCount;
    Integer commentCount;
    Integer viewCount;

    public PostInfoDTO(Integer id, String time, int userId, String userName, String title, String announce, Integer likeCount, Integer dislikeCount, Integer commentCount, Integer viewCount) {
        this.id = id;   //post-id
        this.time = time;   //post-time
        this.user = new UserInfo(userId, userName);   //user-id,name
        this.title = title;     //post-title
        this.announce = announce;   //post-text
        this.likeCount = likeCount;     //post_votes-value(1)(count)
        this.dislikeCount = dislikeCount;   //post_votes-value(-1)(count)
        this.commentCount = commentCount;   //post_comments(count)
        this.viewCount = viewCount;     //post-view_count
    }

    public Integer getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public UserInfo getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getAnnounce() {
        return announce;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    //User info class
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
