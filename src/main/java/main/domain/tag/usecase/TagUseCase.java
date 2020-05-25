package main.domain.tag.usecase;

import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagUseCase {
    @Autowired
    TagRepositoryPort tagRepositoryPort;

    public List<Tag> getTags(String query){
        return tagRepositoryPort.findAll();
    }

    public void save(Tag tag){
        tagRepositoryPort.save(tag);
    }
}
