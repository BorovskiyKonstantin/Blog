package main.dao.post;

import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostRepositoryPortImpl implements PostRepositoryPort {
    private PostRepository postRepository;
    private TagRepositoryPort tagRepositoryPort;

    @Autowired
    public PostRepositoryPortImpl(PostRepository postRepository, TagRepositoryPort tagRepositoryPort) {
        this.postRepository = postRepository;
        this.tagRepositoryPort = tagRepositoryPort;
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Integer getActivePostsCount() {
        return postRepository.getActivePostsCount();
    }

    @Override
    public List<Post> getAllPosts(String mode) {
        switch (mode) {
            case "recent":
                return postRepository.getPostsRecentMode();

            case "popular":
                return postRepository.getPostsPopularMode();

            case "best":
                return postRepository.getPostsBestMode();

            case "early":
                return postRepository.getPostsEarlyMode();

        }
        throw new IllegalArgumentException("Illegal argument: mode");
    }

    @Override
    public List<Post> getActivePostsByModerationStatus(ModerationStatus moderationStatus, Integer moderatorId) {
        return postRepository.getActivePostsByModerationStatus(moderationStatus, moderatorId);
    }

    @Override
    public List<Post> searchPosts(String query) {
        return postRepository.searchPosts(query);
    }

    @Override
    public Optional<Post> getActivePostById(Integer id) {
        return postRepository.getActiveById(id);
    }

    @Override
    public List<Post> getPostsByDate(String date) {
        return postRepository.getPostsByDate(date);
    }

    @Override
    public List<Post> getPostsByTag(String tag) {
        return postRepository.getPostsByTag(tag);
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> getCurrentUserPosts(int currentUserId, String status) {
        boolean isActive;
        ModerationStatus moderationStatus;
        switch (status) {
            case "inactive":
                isActive = false;
                moderationStatus = null;
                break;
            case "pending":
                isActive = true;
                moderationStatus = ModerationStatus.NEW;
                break;
            case "declined":
                isActive = true;
                moderationStatus = ModerationStatus.DECLINED;
                break;
            case "published":
                isActive = true;
                moderationStatus = ModerationStatus.ACCEPTED;
                break;
            default:
                throw new IllegalArgumentException("Illegal argument: status");
        }

        return postRepository.getCurrentUserPosts(currentUserId, isActive, moderationStatus);
    }

    @Override
    public Post save(Post post) {
        List<Tag> tagsA = post.getTags();
        //сохранить тэги, т.к. поле name уникально
        tagsA.forEach(t -> tagRepositoryPort.save(t));
        return postRepository.save(post);
    }

}
