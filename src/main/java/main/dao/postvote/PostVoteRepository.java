package main.dao.postvote;

import main.domain.postvote.entity.PostVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends CrudRepository<PostVote,Integer> {

    @Query(value = "SELECT COUNT(value) FROM post_votes WHERE post_id = ?1 AND value = 1", nativeQuery = true)
    Integer getLikeCountByPostId(int id);

    @Query(value = "SELECT COUNT(value) FROM post_votes WHERE post_id = ?1 AND value = 0", nativeQuery = true)
    Integer getLikeDislikeByPostId(int id);

    Optional<PostVote> getByUserIdAndPostId(Integer currentUserId, Integer postId);
}
