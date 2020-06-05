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
    public void save(Tag tag) {
        tagRepository.save(tag);
    }
}
