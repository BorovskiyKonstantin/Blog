package main.domain.postcomments.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentDTO {
    private int id;
    private Timestamp time;
    private String text;
    private Map<String, Object> user;

    public PostCommentDTO(int id, Timestamp time, String text, Map<String, Object> userDTO) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.user = userDTO;
    }

    public PostCommentDTO(int id, Timestamp time, String text, int userId, String userName, String userPhoto) {
        this.id = id;
        this.time = time;
        this.text = text;

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id",userId);
        user.put("name",userName);
        user.put("photo",userPhoto);
        this.user = user;
    }
}
