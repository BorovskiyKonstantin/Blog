package main.domain.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostSaveResponseDTO {
    private Boolean result;
    private Map<String, String> errors;

    public PostSaveResponseDTO(boolean result) {
        this.result = result;
    }

    public PostSaveResponseDTO(Map<String, String> errors) {
        this.result = false;
        this.errors = errors;
    }
}
