package main.dao.postvote;

import main.domain.postvote.entity.PostVote;
import main.domain.postvote.entity.PostVoteType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends CrudRepository<PostVote,Integer> {

    @Query(value = "SELECT COUNT(value) FROM post_votes WHERE post_id = ?1 AND value = 1", nativeQuery = true)
    Integer getLikeCountByPostId(int id);

    @Query(value = "SELECT COUNT(value) FROM post_votes WHERE post_id = ?1 AND value = -1", nativeQuery = true)
    Integer getDislikeByPostId(int id);

    Optional<PostVote> getByUserIdAndPostId(Integer currentUserId, Integer postId);

    @Query(value = "SELECT count(*) FROM PostVote pv\n" +
            "JOIN Post p ON pv.id = p.id\n" +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND (:id IS NULL OR p.userId = :id)\n" +
            "AND pv.value = :voteType" )
    int getVoteCountForUser(@Param("id")Integer userId,
                            @Param("voteType")PostVoteType voteType);
}
