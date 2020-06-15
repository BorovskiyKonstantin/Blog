package main.dao.postcomments;

import main.domain.postcomments.entity.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends CrudRepository<PostComment, Integer> {
    @Query(value = "SELECT count(*) FROM blog.post_comments WHERE post_id = ?1", nativeQuery = true)
    Integer getCommentCountByPostId(int id);

    List<PostComment> findAllByPostId(int id);
}
