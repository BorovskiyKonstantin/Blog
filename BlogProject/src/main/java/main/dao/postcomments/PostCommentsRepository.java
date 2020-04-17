package main.dao.postcomments;

import main.domain.postcomments.entity.PostComments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentsRepository extends CrudRepository<PostComments, Integer> {
    @Query(value = "SELECT count(*) FROM blog.post_comments WHERE post_id = ?1", nativeQuery = true)
    Integer getCommentCountByPostId(int id);
}
