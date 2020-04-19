package main.dao.tag;

import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagRepositoryPortImpl implements TagRepositoryPort {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }
}