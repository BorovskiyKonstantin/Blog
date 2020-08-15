package main.domain.user.model.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeProfileRequestDTO {
    private @JsonProperty("email") String email;
    private @JsonProperty("name")String name;
    private @JsonProperty("password")String password;
    private @JsonProperty("removePhoto")Integer removePhoto;
    private @JsonProperty("photo")String photo;
}
