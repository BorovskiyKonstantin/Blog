package main.domain.postcomments.port;

public interface PostCommentsRepositoryPort {
    Integer getCommentCountByPostId(int id);
}
