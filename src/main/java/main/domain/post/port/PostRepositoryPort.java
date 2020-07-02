package main.domain.post.port;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;
import main.domain.post.model.PostRequestDTO;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {
    List<Post> findAll();

    List<Post> getAllPosts(String mode);

    List<Post> getByModerationStatus(ModerationStatus moderationStatus);

    List<Post> searchPosts(String query);

    Optional<Post> getPostById(Integer id);

    List<Post> getPostsByDate(String date);
}
