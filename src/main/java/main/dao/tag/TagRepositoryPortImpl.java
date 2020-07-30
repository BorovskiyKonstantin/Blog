package main.dao.tag;

import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagRepositoryPortImpl implements TagRepositoryPort {
    private TagRepository tagRepository;

    @Autowired
    public TagRepositoryPortImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public Tag save(Tag tag) {
        tagRepository.findOneByName(tag.getName())
                .ifPresentOrElse(
                        foundTag -> tag.setId(foundTag.getId()),
                        () -> tag.setId(tagRepository.save(tag).getId()));
        return tag;
    }

    @Override
    public List<Tag> searchTagsStartingWith(String query) {
        return tagRepository.findByNameStartingWith(query);
    }

    @Override
    public Integer findActivePostsCountByTagId(int tagId) {
        return tagRepository.findActivePostsCountByTag(tagId);
    }
}
