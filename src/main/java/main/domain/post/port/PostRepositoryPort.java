package main.domain.post.port;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {
    List<Post> findAll();

    List<Post> getAllPosts(String mode);

    List<Post> getActivePostsByModerationStatus(ModerationStatus moderationStatus, Integer moderatorId);

    List<Post> searchPosts(String query);

    Optional<Post> getActivePostById(Integer id);

    List<Post> getPostsByDate(String date);

    List<Post> getPostsByTag(String tag);

    Optional<Post> findPostById(int postId);

    List<Post> getCurrentUserPosts(int currentUserId, String status);

    Post save(Post post);
}
