package main.domain.postcomments.port;

import main.domain.postcomments.entity.PostComment;

import java.util.List;
import java.util.Optional;

public interface PostCommentsRepositoryPort {
    List<PostComment> getCommentsByPostId(int id);

    void save(PostComment postComment);

    Optional<PostComment> findCommentByIdForPostWithId(Integer commentId, Integer postId);
}
