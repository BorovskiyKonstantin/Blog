package main.dao.postcomments;

import main.domain.postcomments.entity.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentsRepository extends CrudRepository<PostComment, Integer> {
    List<PostComment> findAllByPostId(int id);

    @Query(value = "SELECT * FROM post_comments pc WHERE id = :commentId AND post_id = :postId", nativeQuery = true)
    Optional<PostComment> findCommentByIdForPostWithId(@Param("commentId")Integer commentId, @Param("postId")Integer postId);
}
