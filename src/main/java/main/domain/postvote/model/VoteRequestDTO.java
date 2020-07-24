package main.domain.postvote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteRequestDTO {
    @JsonProperty("post_id")
    private Integer postId;

    public Integer getPostId() {
        return postId;
    }
}
