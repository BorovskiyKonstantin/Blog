package main.domain.postvote.port;

public interface PostVoteRepositoryPort {
    Integer getLikeCountByPostId(int id);
    Integer getDislikeCountByPostId(int id);
}
