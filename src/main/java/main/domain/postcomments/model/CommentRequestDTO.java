package main.domain.postcomments.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @JsonProperty(value = "parent_id")
    private Integer parentId;
    @JsonProperty(value = "post_id")
    private Integer postId;
    @JsonProperty(value = "text", required = true)
    private String text;
}
