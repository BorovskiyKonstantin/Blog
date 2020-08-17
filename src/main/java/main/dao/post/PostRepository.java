package main.dao.post;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    //Строка с условием для фильтрации в запросе активных, одобренных медератором постов с датой публикации < текущего момента.
    String activePostsFilter = "p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW()";

    //recent   -   сортировать   по   дате   публикации,   выводить   сначала   новые
    @Query(value = "SELECT * FROM posts p WHERE " + activePostsFilter + " ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsRecentMode();

    //popular   -   сортировать   по   убыванию   количества   комментариев
    @Query(value = "SELECT * FROM posts p LEFT JOIN post_comments comm ON p.id = comm.post_id WHERE " + activePostsFilter + " GROUP BY p.id ORDER BY COUNT(comm.id) DESC", nativeQuery = true)
    List<Post> getPostsPopularMode();

    //best   -   сортировать   по   убыванию   количества   лайков
    @Query(value = "SELECT * FROM posts p LEFT JOIN post_votes v ON p.id = v.post_id WHERE " + activePostsFilter + " GROUP BY p.id ORDER BY sum(value = 1) DESC", nativeQuery = true)
    List<Post> getPostsBestMode();

    //early   -   сортировать   по   дате   публикации,   выводить   сначала   старые
    @Query(value = "SELECT * FROM posts p WHERE " + activePostsFilter + " ORDER BY p.time DESC", nativeQuery = true)
    List<Post> getPostsEarlyMode();

    @Query(value = "SELECT p FROM Post p WHERE p.moderationStatus = :status AND (:moderatorId IS NULL OR p.moderatorId = :moderatorId) AND p.isActive = 1 ORDER BY p.time DESC")
    List<Post> getActivePostsByModerationStatus(@Param("status") ModerationStatus moderationStatus,
                                                @Param("moderatorId") Integer moderatorId);

    @Query(value = "SELECT * FROM posts p WHERE p.title LIKE %:query% AND " + activePostsFilter + " ORDER BY p.time", nativeQuery = true)
    List<Post> searchPosts(@Param("query") String searchQuery);

    @Query(value = "SELECT * FROM posts p WHERE p.id = :id AND " + activePostsFilter + " ORDER BY p.time", nativeQuery = true)
    Optional<Post> getActiveById(@Param("id") int id);

    @Query(value = "SELECT * FROM posts p WHERE " + activePostsFilter + " AND cast(p.time as date) = :date ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM posts p\n" +
            "JOIN tag2post tp ON p.id = tp.post_id\n" +
            "JOIN tags t ON t.id = tp.tag_id\n" +
            "WHERE " +
            "t.name = :tag\n" +
            "AND " + activePostsFilter + " ORDER BY p.time", nativeQuery = true)
    List<Post> getPostsByTag(@Param("tag") String tag);

    @Query("SELECT p FROM Post p WHERE p.userId = :userId AND p.isActive = :isActive AND (:status IS NULL or p.moderationStatus = :status)")
    List<Post> getCurrentUserPosts(@Param("userId")int userId,
                                   @Param("isActive")boolean isActive,
                                   @Param("status")ModerationStatus moderationStatus);

    @Query("SELECT count(*) FROM Post p WHERE (:userId IS NULL OR p.userId = :userId) AND p.isActive = :isActive AND (:status IS NULL or p.moderationStatus = :status)")
    int getCurrentUserPostsCount(@Param("userId")Integer userId,
                                     @Param("isActive")boolean isActive,
                                     @Param("status")ModerationStatus status);

    @Query(value = "SELECT count(*) FROM posts p WHERE " + activePostsFilter, nativeQuery = true)
    Integer getActivePostsCount();


    @Modifying
    @Query(value = "UPDATE posts p \n" +
            "SET moderation_status = :status , moderator_id = :moderatorId\n" +
            "WHERE id = :postId", nativeQuery = true)
    int setPostModeration(@Param("postId")int postId,
                          @Param("status") String moderationStatus,
                          @Param("moderatorId")int moderatorId);

    @Query(value = "SELECT DISTINCT YEAR(time) FROM posts p\n" +
            "WHERE " + activePostsFilter + " ORDER BY p.time" , nativeQuery = true)
    List<Integer> getYearsOfPublications();

    @Query(value = "SELECT DATE(time) as date, count(DATE(time)) as count FROM posts p\n" +
            "WHERE YEAR(time) = :year " +
            "AND " + activePostsFilter + " GROUP BY date ORDER BY YEAR(date), count DESC", nativeQuery = true)
    List<Object[]> getPublicationsCountByYear(@Param("year") Integer year);

    @Query(value = "SELECT sum(view_count) FROM posts p\n" +
            "WHERE p.is_active = true AND moderation_status = 'ACCEPTED' AND (:id IS NULL OR p.user_id = :id)", nativeQuery = true)
    int getViewsCountForUser(@Param("id") Integer id);

    @Query(value = "SELECT time FROM posts p\n" +
            "WHERE p.is_active = true AND moderation_status = 'ACCEPTED' AND (:id IS NULL OR p.user_id = :id)\n" +
            "ORDER BY time LIMIT 1", nativeQuery = true)
    Timestamp getFirstPublicationTimeForUser(@Param("id")Integer id);
}
