package main.domain.post.port;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.postvote.entity.PostVoteType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostRepositoryPort {
    List<Post> findAll();

    Integer getActivePostsCount();

    List<Post> getAllPosts(String mode);

    List<Post> getActivePostsByModerationStatus(ModerationStatus moderationStatus, Integer moderatorId);

    List<Post> searchPosts(String query);

    Optional<Post> getActivePostById(Integer id);

    List<Post> getPostsByDate(String date);

    List<Post> getPostsByTag(String tag);

    Optional<Post> findPostById(int postId);

    List<Post> getCurrentUserPosts(int currentUserId, ModerationStatus moderationStatus, boolean isActive);

    int getCurrentUserPostsCount(Integer userId, ModerationStatus accepted, boolean b);

    Post save(Post post);

    int setPostModeration(int postId, ModerationStatus moderationStatus, int moderatorId);

    List<Integer> getYearsOfPublications();

    Map<String, Integer> getPublicationsCountByYear(Integer year);

    int getVotesCount(Integer userId, PostVoteType voteType);

    int getViewsCount(Integer userId);

    Timestamp getFirstPublicationTimeForUser(Integer userId);
}
