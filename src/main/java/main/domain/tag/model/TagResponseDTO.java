package main.domain.tag.model;

import java.util.List;

public class TagResponseDTO {
    private List<TagDTO> tags;

    public TagResponseDTO(List<TagDTO> tags) {
        this.tags = tags;
    }

    public List<TagDTO> getTags() {
        return tags;
    }
}

