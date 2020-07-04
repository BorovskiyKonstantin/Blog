package main.dao.post;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    //recent   -   сортировать   по   дате   публикации,   выводить   сначала   новые
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 AND p.is_active = 1 ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsRecentMode(Timestamp currentTime);

    //popular   -   сортировать   по   убыванию   количества   комментариев
    @Query(value = "SELECT * FROM posts p LEFT JOIN post_comments comm ON p.id = comm.post_id WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 AND p.is_active = 1 GROUP BY p.id ORDER BY COUNT(comm.id) DESC", nativeQuery = true)
    List<Post> getPostsPopularMode(Timestamp currentTime);

    //best   -   сортировать   по   убыванию   количества   лайков
    @Query(value = "SELECT * FROM posts p LEFT JOIN post_votes v ON p.id = v.post_id WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 AND p.is_active = 1 GROUP BY p.id ORDER BY sum(value = 1) DESC", nativeQuery = true)
    List<Post> getPostsBestMode(Timestamp currentTime);

    //early   -   сортировать   по   дате   публикации,   выводить   сначала   старые
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 AND p.is_active = 1 ORDER BY p.time DESC", nativeQuery = true)
    List<Post> getPostsEarlyMode(Timestamp currentTime);

    @Query(value = "SELECT p FROM Post p WHERE p.moderationStatus = ?1")
    List<Post> getByModerationStatus(ModerationStatus moderationStatus);

    @Query(value = "SELECT * FROM posts p WHERE p.title LIKE %?1% AND p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?2 AND p.is_active = 1 ORDER BY p.time", nativeQuery = true)
    List<Post> searchPosts(String query, Timestamp currentTime);

    @Query(value = "SELECT * FROM posts p WHERE p.id = ?1 AND p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?2 AND p.is_active = 1 ORDER BY p.time", nativeQuery = true)
    Optional<Post> getById(int id, Timestamp currentTime);

    @Query(value = "SELECT * FROM posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 AND p.is_active = 1 AND cast(p.time as date) = ?2 ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsByDate(Timestamp currentTime, String date);

    @Query(value = "SELECT * FROM posts p\n" +
            "JOIN tag2post tp ON p.id = tp.post_id\n" +
            "JOIN tags t ON t.id = tp.tag_id\n" +
            "WHERE t.name = ?1\n" +
            "AND p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?2 AND p.is_active = 1 ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsByTag(String tag, Timestamp currentTime);
}
