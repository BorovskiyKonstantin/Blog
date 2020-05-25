package main.domain.user.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.domain.user.entity.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private boolean result;
    private UserInfo userInfo;

    private AuthResponseDTO(boolean result){
        this.result = result;
        this.userInfo = null;
    }

    private AuthResponseDTO(boolean result, int userId, String userName, String photo, String email, boolean isModerator, int moderationCount, boolean settings) {
        this.result = result;
        this.userInfo = new UserInfo(userId, userName, photo, email, isModerator, moderationCount, settings);
    }

    public boolean isResult() {
        return result;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    //User info
    private class UserInfo {
        private int id;
        private String name;
        private String photo;
        private String email;
        private boolean isModerator;
        private int moderationCount;
        private boolean settings;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getEmail() {
            return email;
        }

        public boolean isModerator() {
            return isModerator;
        }

        public int getModerationCount() {
            return moderationCount;
        }

        public boolean isSettings() {
            return settings;
        }

        private UserInfo(int id, String name, String photo, String email, boolean isModerator, int moderationCount, boolean settings) {
            this.id = id;
            this.name = name;
            this.photo = photo;
            this.email = email;
            this.isModerator = isModerator;
            this.moderationCount = moderationCount;
            this.settings = settings;
        }
    }

    public static AuthResponseDTO failed(){
        return new AuthResponseDTO(false);
    }

    public static AuthResponseDTO successfulLogIn(User user){
        int id = user.getId();
        String name = user.getName();
        String photo = user.getPhoto();
        String email = user.getEmail();
        boolean moderation = user.isModerator();
        int moderationCount = user.getModeratedPosts().size();
        boolean settings = user.isModerator();

        return new AuthResponseDTO(true, id, name, photo, email, moderation, moderationCount, settings);
    }

    public static AuthResponseDTO logOut(){
        return new AuthResponseDTO(true);
    }
}
