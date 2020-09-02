package main.dao.post;

import main.dao.postvote.PostVoteRepository;
import main.domain.post.entity.ModerationStatus;
import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postvote.entity.PostVoteType;
import main.domain.tag.entity.Tag;
import main.domain.tag.port.TagRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Component
public class PostRepositoryPortImpl implements PostRepositoryPort {
    private PostRepository postRepository;
    private TagRepositoryPort tagRepositoryPort;
    private PostVoteRepository postVoteRepository;

    @Autowired
    public PostRepositoryPortImpl(PostRepository postRepository, TagRepositoryPort tagRepositoryPort, PostVoteRepository postVoteRepository) {
        this.postRepository = postRepository;
        this.tagRepositoryPort = tagRepositoryPort;
        this.postVoteRepository = postVoteRepository;
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
    public Page<Post> getAllPosts(int offset, int limit, String mode) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);

        switch (mode) {
            case "recent":
                return postRepository.getPostsRecentMode(pageable);

            case "popular":
                return postRepository.getPostsPopularMode(pageable);

            case "best":
                return postRepository.getPostsBestMode(pageable);

            case "early":
                return postRepository.getPostsEarlyMode(pageable);
        }
        throw new IllegalArgumentException("Illegal argument: mode");
    }

    @Override
    public Page<Post> getActivePostsByModerationStatus(int offset, int limit, ModerationStatus moderationStatus, Integer moderatorId) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.getActivePostsByModerationStatus(moderationStatus, moderatorId, pageable);
    }

    @Override
    public Page<Post> searchPosts(int offset, int limit, String query) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.searchPosts(query, pageable);
    }

    @Override
    public Optional<Post> getActivePostById(Integer id) {
        return postRepository.getActiveById(id);
    }

    @Override
    public Page<Post> getPostsByDate(int offset, int limit, String date) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.getPostsByDate(date, pageable);
    }

    @Override
    public Page<Post> getPostsByTag(int offset, int limit, String tag) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.getPostsByTag(tag, pageable);
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Page<Post> getCurrentUserPosts(int offset, int limit, int currentUserId, ModerationStatus moderationStatus, boolean isActive) {
        int page = offset/10;
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.getCurrentUserPosts(currentUserId, isActive, moderationStatus, pageable);
    }

    @Override
    public int getCurrentUserPostsCount(Integer userId, ModerationStatus status, boolean isActive) {
        return postRepository.getCurrentUserPostsCount(userId, isActive, status);
    }

    @Override
    public Post save(Post post) {
        //todo сохранение тэгов создает запросы и не всегда требуется
        List<Tag> tagsA = post.getTags();
        //сохранить тэги, т.к. поле name уникально
        tagsA.forEach(t -> tagRepositoryPort.save(t));
        return postRepository.save(post);
    }

    @Override
    public int setPostModeration(int postId, ModerationStatus moderationStatus, int moderatorId) {
        return postRepository.setPostModeration(postId, moderationStatus.name(), moderatorId);
    }

    @Override
    public List<Integer> getYearsOfPublications() {
        return postRepository.getYearsOfPublications();
    }

    @Override
    public Map<String, Integer> getPublicationsCountByYear(Integer year) {
        List<Object[]> publicationsCountByYear = postRepository.getPublicationsCountByYear(year);
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        publicationsCountByYear.forEach(e -> resultMap.put(
                (e[0]).toString(), ((BigInteger)e[1]).intValue())
        );
        return resultMap;
    }

    @Override
    public int getVotesCount(Integer userId, PostVoteType voteType) {
        return postVoteRepository.getVoteCountForUser(userId, voteType);
    }

    @Override
    public int getViewsCount(Integer userId) {
        return postRepository.getViewsCountForUser(userId);
    }

    @Override
    public Timestamp getFirstPublicationTimeForUser(Integer userId) {
        return postRepository.getFirstPublicationTimeForUser(userId);
    }

    @Override
    public int getLikesCountByPostId(int id) {
        return postVoteRepository.getLikeCountByPostId(id);
    }

    @Override
    public int getDislikesCountByPostId(int id) {
        return postVoteRepository.getDislikeByPostId(id);
    }

    @Override
    public int getNewActivePostsCount() {
        return postRepository.getNewActivePostsCount();
    }
}
