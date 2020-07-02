package main.dao.post;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PostRepositoryPortImpl implements PostRepositoryPort {
    private PostRepository postRepository;

    @Autowired
    public PostRepositoryPortImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public List<Post> getAllPosts(String mode) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        switch (mode) {
            case "recent":
                return postRepository.getPostsRecentMode(currentTime);

            case "popular":
                return postRepository.getPostsPopularMode(currentTime);

            case "best":
                return postRepository.getPostsBestMode(currentTime);

            case "early":
                return postRepository.getPostsEarlyMode(currentTime);

        }
        throw new IllegalArgumentException("Illegal argument: mode");
    }

    @Override
    public List<Post> getByModerationStatus(ModerationStatus moderationStatus) {
        return postRepository.getByModerationStatus(moderationStatus);
    }

    @Override
    public List<Post> searchPosts(String query) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        return postRepository.searchPosts(query, currentTime);
    }

    @Override
    public Optional<Post> getPostById(Integer id) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        return postRepository.getById(id, currentTime);
    }

    @Override
    public List<Post> getPostsByDate(String date) {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        return postRepository.getPostsByDate(currentTime, date);
    }

}
