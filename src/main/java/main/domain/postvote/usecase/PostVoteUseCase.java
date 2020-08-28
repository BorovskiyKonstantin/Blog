package main.domain.postvote.usecase;

import main.domain.postvote.entity.PostVote;
import main.domain.postvote.entity.PostVoteType;
import main.domain.postvote.model.VoteRequestDTO;
import main.domain.postvote.model.VoteResponseDTO;
import main.domain.postvote.port.PostVoteRepositoryPort;
import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PostVoteUseCase {
    private PostVoteRepositoryPort voteRepositoryPort;

    @Autowired
    public PostVoteUseCase(PostVoteRepositoryPort voteRepositoryPort) {
        this.voteRepositoryPort = voteRepositoryPort;
    }

    public VoteResponseDTO votePost(VoteRequestDTO requestDTO, PostVoteType voteType, int currentUserId) {
        Integer postId = requestDTO.getPostId();

        PostVote postVote = voteRepositoryPort.getPostVote(currentUserId, postId).orElse(new PostVote());
        if (postVote.getValue() == voteType)
            return new VoteResponseDTO(false);

        postVote.setValue(voteType);
        postVote.setPostId(postId);
        postVote.setUserId(currentUserId);
        postVote.setTime(Timestamp.valueOf(LocalDateTime.now()));
        voteRepositoryPort.save(postVote);
        return new VoteResponseDTO(true);
    }
}
