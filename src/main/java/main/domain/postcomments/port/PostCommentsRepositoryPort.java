package main.domain.postcomments.port;

import main.domain.postcomments.entity.PostComment;

import java.util.List;

public interface PostCommentsRepositoryPort {
    Integer getCommentCountByPostId(int id);

    List<PostComment> getCommentsByPostId(int id);
}
