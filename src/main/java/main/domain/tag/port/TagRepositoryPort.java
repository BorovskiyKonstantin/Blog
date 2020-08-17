package main.domain.tag.port;

import main.domain.tag.entity.Tag;

import java.util.List;

public interface TagRepositoryPort {
    List<Tag> findAll();

    Tag save(Tag tag);

    List<Tag> searchTagsStartingWith(String query);

    Integer findActivePostsCountByTagId(int tagId);
}
