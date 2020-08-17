package main.dao.postvote;

import main.domain.postvote.entity.PostVote;
import main.domain.postvote.port.PostVoteRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostVotePortImpl implements PostVoteRepositoryPort {
    private PostVoteRepository postVoteRepository;

    @Autowired
    public PostVotePortImpl(PostVoteRepository postVoteRepository) {
        this.postVoteRepository = postVoteRepository;
    }

    @Override
    public Integer getLikeCountByPostId(int id) {
        return postVoteRepository.getLikeCountByPostId(id);
    }

    @Override
    public Integer getDislikeCountByPostId(int id) {
        return postVoteRepository.getLikeDislikeByPostId(id);
    }

    @Override
    public Optional<PostVote> getPostVote(Integer currentUserId, Integer postId) {
        return postVoteRepository.getByUserIdAndPostId(currentUserId,postId);
    }

    @Override
    public PostVote save(PostVote postVote) {
        return postVoteRepository.save(postVote);
    }
}
