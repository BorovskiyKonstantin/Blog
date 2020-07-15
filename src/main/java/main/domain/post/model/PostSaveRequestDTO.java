package main.domain.post.model;

import lombok.Data;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PostSaveRequestDTO {
    private Timestamp time;
    private boolean active;
    private String title;
    private String[] tags;
    private String text;

    public void setTime(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = dateFormat.parse(time);
            this.time = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
