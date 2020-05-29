package main.domain.post.port;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;

import java.util.List;

public interface PostRepositoryPort {
    List<Post> findAll();

    List<Post> getAllPosts(String mode);

    List<Post> getNewPosts();

    List<Post> searchPosts(String query);
}
