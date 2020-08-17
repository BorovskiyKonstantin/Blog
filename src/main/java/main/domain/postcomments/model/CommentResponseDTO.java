package main.domain.postcomments.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    private Integer id;
    private long timestamp;
    private String text;
    private Map<String, Object> user;
    private Boolean result;
    private Map<String, String> errors;

    public CommentResponseDTO(int id, Timestamp timestamp, String text, Map<String, Object> userDTO) {
        this.id = id;
        this.timestamp = timestamp.getTime();
        this.text = text;
        this.user = userDTO;
    }

    public CommentResponseDTO(int id, Timestamp timestamp, String text, int userId, String userName, String userPhoto) {
        this.id = id;
        this.timestamp = timestamp.getTime();
        this.text = text;

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id",userId);
        user.put("name",userName);
        user.put("photo",userPhoto);
        this.user = user;
    }

    public CommentResponseDTO(Map<String,String> errors){
        this.result = false;
        this.errors = errors;
    }
}
