package main.domain.tag.usecase;

import main.domain.post.port.PostRepositoryPort;
import main.domain.tag.entity.Tag;
import main.domain.tag.model.TagDTO;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TagUseCase {
    private TagRepositoryPort tagRepositoryPort;
    private PostRepositoryPort postRepositoryPort;

    @Autowired
    public TagUseCase(TagRepositoryPort tagRepositoryPort, PostRepositoryPort postRepositoryPort) {
        this.tagRepositoryPort = tagRepositoryPort;
        this.postRepositoryPort = postRepositoryPort;
    }

    public List<TagDTO> getTags(String query) {
        if(query == null)
            query = "";
        List<Tag> tags = tagRepositoryPort.searchTagsStartingWith(query);   //список тэгов по запросу
        Integer postsCount = postRepositoryPort.getActivePostsCount();  //общее количество активных постов на сайте
        double maxWeight = 0.0; //максимальный вес поста (нормированный вес = 1.0)

        //map с весами(weight) тегов
        Map<Tag, Double> tagWeights = new HashMap<>();
        for (Tag tag : tags) {
            Integer postsWithTagCount = tagRepositoryPort.findActivePostsCountByTagId(tag.getId());
            if(postsWithTagCount == 0)
                continue;
            double weight = (double) postsWithTagCount/ postsCount;    //расчет веса
            if (maxWeight < weight) maxWeight = weight;
            tagWeights.put(tag, weight);
        }

        //список с DTO
        List<TagDTO> responseDTO = new ArrayList<>();
        for(Map.Entry<Tag, Double> e : tagWeights.entrySet()){
            String tagName = e.getKey().getName();
            double weight = e.getValue();
            double normWeight = weight/maxWeight; //нормализованный вес
            responseDTO.add(new TagDTO(tagName, normWeight));
        }
        responseDTO.sort((o1, o2) -> Double.compare(o2.getWeight(), o1.getWeight()));
        return responseDTO;
    }

    public void save(Tag tag){
        tagRepositoryPort.save(tag);
    }
}
