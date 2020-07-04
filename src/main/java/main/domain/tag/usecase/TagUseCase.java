package main.domain.tag.usecase;

import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagUseCase {
    private TagRepositoryPort tagRepositoryPort;

    @Autowired
    public TagUseCase(TagRepositoryPort tagRepositoryPort) {
        this.tagRepositoryPort = tagRepositoryPort;
    }

    public List<Tag> getTags(String query){
        return tagRepositoryPort.findAll();
    }

    public void save(Tag tag){
        tagRepositoryPort.save(tag);
    }
}
