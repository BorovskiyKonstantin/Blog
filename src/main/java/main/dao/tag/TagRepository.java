package main.dao.tag;

import main.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository <Tag, Integer> {
    List<Tag> findByNameStartingWith(String query);

    @Query(value = "SELECT count(*) FROM tags t \n" +
            "JOIN tag2post tp ON t.id = tp.tag_id\n" +
            "JOIN posts p ON p.id = tp.post_id\n" +
            "WHERE t.id = :id AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW()", nativeQuery = true)
    Integer findActivePostsCountByTag(@Param("id") Integer tagId);

    Optional<Tag> findOneByName (String name);

}
