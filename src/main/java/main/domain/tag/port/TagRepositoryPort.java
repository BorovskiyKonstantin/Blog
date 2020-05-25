package main.domain.tag.port;

import main.domain.tag.entity.Tag;

import java.util.List;

public interface TagRepositoryPort {
    List<Tag> findAll();
    void save(Tag tag);
}
