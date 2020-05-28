package main.dao.post;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    @Override
    long count();

    //recent   -   сортировать   по   дате   публикации,   выводить   сначала   новые
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 ORDER BY p.time limit ?3 offset ?2", nativeQuery = true)
    List<Post> getPostsRecentMode(Timestamp currentTime, int offset, int limit);

    //popular   -   сортировать   по   убыванию   количества   комментариев
    @Query(value = "SELECT * FROM posts p LEFT JOIN blog.post_comments comm ON p.id = comm.post_id WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 GROUP BY p.id ORDER BY COUNT(comm.id) DESC  limit ?3 offset ?2", nativeQuery = true)
    List<Post> getPostsPopularMode(Timestamp currentTime, int offset, int limit);

    //best   -   сортировать   по   убыванию   количества   лайков
    @Query(value = "SELECT * FROM posts p JOIN blog.post_votes v ON p.id = v.post_id WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 GROUP BY p.id ORDER BY sum(value = 1) DESC  limit ?3 offset ?2", nativeQuery = true)
    List<Post> getPostsBestMode(Timestamp currentTime, int offset, int limit);

    //early   -   сортировать   по   дате   публикации,   выводить   сначала   старые
    @Query(value = "SELECT * FROM posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' AND p.time < ?1 ORDER BY p.time DESC limit ?3 offset ?2", nativeQuery = true)
    List<Post> getPostsEarlyMode(Timestamp currentTime, int offset, int limit);

    @Query(value = "SELECT * FROM posts p WHERE p.moderation_status = ?1", nativeQuery = true)
    List<Post> getNewPosts(String moderationStatus);
}
