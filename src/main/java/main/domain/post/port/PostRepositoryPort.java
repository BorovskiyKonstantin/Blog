package main.domain.post.port;

import main.domain.post.entity.Post;
import main.domain.post.model.PostInfoDTO;

import java.util.List;

public interface PostRepositoryPort {
    List<Post> findAll();

    long count();

    List<Post> getAllPosts(int offset, int limit, String mode);

    List<Post> getNewPosts();
}
