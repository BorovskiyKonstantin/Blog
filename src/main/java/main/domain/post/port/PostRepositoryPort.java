package main.domain.post.port;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.postvote.entity.PostVoteType;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostRepositoryPort {
    List<Post> findAll();

    Integer getActivePostsCount();

    Page<Post> getAllPosts(int offset, int limit, String mode);

    Page<Post> getActivePostsByModerationStatus(int offset, int limit, ModerationStatus moderationStatus, Integer moderatorId);

    Page<Post> searchPosts(int offset, int limit, String query);

    Optional<Post> getActivePostById(Integer id);

    Page<Post> getPostsByDate(int offset, int limit, String date);

    Page<Post> getPostsByTag(int offset, int limit, String tag);

    Optional<Post> findPostById(int postId);

    Page<Post> getCurrentUserPosts(int offset, int limit, int currentUserId, ModerationStatus moderationStatus, boolean isActive);

    int getCurrentUserPostsCount(Integer userId, ModerationStatus accepted, boolean b);

    Post save(Post post);

    int setPostModeration(int postId, ModerationStatus moderationStatus, int moderatorId);

    List<Integer> getYearsOfPublications();

    Map<String, Integer> getPublicationsCountByYear(Integer year);

    int getVotesCount(Integer userId, PostVoteType voteType);

    int getViewsCount(Integer userId);

    Timestamp getFirstPublicationTimeForUser(Integer userId);

    int getLikesCountByPostId(int id);

    int getDislikesCountByPostId(int id);

    int getNewActivePostsCount();
}
