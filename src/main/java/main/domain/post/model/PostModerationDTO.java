package main.domain.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostModerationDTO {
    private @JsonProperty("post_id")int postId;
    private @JsonProperty("decision")String decision;
}
