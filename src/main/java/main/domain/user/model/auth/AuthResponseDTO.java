package main.domain.user.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import main.domain.user.entity.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"result", "user"})
public class AuthResponseDTO {
    private @JsonProperty("result")boolean result;
    private @JsonProperty("user")UserInfo userInfo;

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
    @JsonPropertyOrder({"id","name","photo","email","moderation","moderationCount","settings"})
    private class UserInfo {
        private @JsonProperty("id")int id;
        private @JsonProperty("name")String name;
        private @JsonProperty("photo")String photo;
        private @JsonProperty("email")String email;
        private @JsonProperty("moderation")boolean moderator;
        private @JsonProperty("moderationCount")int moderationCount;
        private @JsonProperty("settings")boolean settings;

        //UserInfo constructor
        private UserInfo(int id, String name, String photo, String email, boolean isModerator, int moderationCount, boolean settings) {
            this.id = id;
            this.name = name;
            this.photo = photo;
            this.email = email;
            this.moderator = isModerator;
            this.moderationCount = moderationCount;
            this.settings = settings;
        }

        //Геттеры для сериализации внутреннего класса в JSON
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
            return moderator;
        }
        public int getModerationCount() {
            return moderationCount;
        }
        public boolean isSettings() {
            return settings;
        }
    }

    public static AuthResponseDTO failedDTO(){
        return new AuthResponseDTO(false);
    }

    public static AuthResponseDTO successfulDTO(User user, int moderationCount){
        return new AuthResponseDTO(
                true,
                user.getId(),
                user.getName(),
                user.getPhoto(),
                user.getEmail(),
                user.isModerator(),
                moderationCount,
                user.isModerator()  //settings
        );
    }

    public static AuthResponseDTO logOutDTO(){
        return new AuthResponseDTO(true);
    }
}
