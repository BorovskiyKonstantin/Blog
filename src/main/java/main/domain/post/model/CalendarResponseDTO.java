package main.domain.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CalendarResponseDTO {
    private @JsonProperty("years") List<Integer> years;
    private @JsonProperty("posts") Map<String, Integer> posts;
}
