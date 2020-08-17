package main.domain.postvote.port;

import main.domain.postvote.entity.PostVote;

import java.util.Optional;

public interface PostVoteRepositoryPort {
    Integer getLikeCountByPostId(int id);
    Integer getDislikeCountByPostId(int id);

    Optional<PostVote> getPostVote(Integer currentUserId, Integer postId);

    PostVote save(PostVote postVote);
}
