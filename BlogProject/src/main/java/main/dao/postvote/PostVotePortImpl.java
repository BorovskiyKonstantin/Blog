package main.dao.postvote;

import main.domain.postvote.port.PostVoteRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostVotePortImpl implements PostVoteRepositoryPort {
    @Autowired
    PostVoteRepository postVoteRepository;

    @Override
    public Integer getLikeCountByPostId(int id) {
        return postVoteRepository.getLikeCountByPostId(id);
    }

    @Override
    public Integer getDislikeCountByPostId(int id) {
        return postVoteRepository.getLikeDislikeByPostId(id);
    }
}
