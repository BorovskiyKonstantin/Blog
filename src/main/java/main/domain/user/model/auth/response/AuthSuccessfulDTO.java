package main.domain.user.model.auth.response;

import main.domain.user.entity.User;

public class AuthSuccessfulDTO implements AuthResponse {
    private final boolean result = true;
    private InnerUserInfo userInfo;

    public AuthSuccessfulDTO(int id, String name, String photo, String email, boolean moderation, int moderationCount, boolean settings) {
        this.userInfo = new InnerUserInfo(id, name, photo, email, moderation, moderationCount, settings);
    }
//
//    public AuthSuccessfulDTO(User user){
//        super;
//    }

    public AuthSuccessfulDTO(InnerUserInfo userInfo){
        this.userInfo = userInfo;
    }


    public boolean isResult() {
        return result;
    }

    public InnerUserInfo getUserInfo() {
        return userInfo;
    }

    //User info
    private class InnerUserInfo {
        private int id;
        private String name;
        private String photo;
        private String email;
        private boolean moderation;
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

        public boolean isModeration() {
            return moderation;
        }

        public int getModerationCount() {
            return moderationCount;
        }

        public boolean isSettings() {
            return settings;
        }

        private InnerUserInfo(int id, String name, String photo, String email, boolean moderation, int moderationCount, boolean settings) {
            this.id = id;
            this.name = name;
            this.photo = photo;
            this.email = email;
            this.moderation = moderation;
            this.moderationCount = moderationCount;
            this.settings = settings;


        }
    }
}
