package main.dao.post;

import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostRepositoryPortImpl implements PostRepositoryPort {
    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public long count() {
        return postRepository.count();
    }

    @Override
    public List<Post> getAllPosts(int offset, int limit, String mode) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        switch (mode) {
            case "recent":
                List<Post> list = postRepository.getPostsRecentMode(currentTime, offset, limit);
                return list;

            case "popular":
                list = postRepository.getPostsPopularMode(currentTime, offset, limit);
                return list;

            case "best":
                return postRepository.getPostsBestMode(currentTime, offset, limit);

            case "early":
                return postRepository.getPostsEarlyMode(currentTime, offset, limit);

        }
        throw new IllegalArgumentException("Illegal argument: mode");
    }
}
