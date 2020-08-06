package main.domain.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PostSaveRequestDTO {
    @JsonProperty("timestamp")
    private Timestamp time;
    private boolean active;
    private String title;
    private String[] tags;
    private String text;
}
