package main.dao.postcomments;

import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostCommentsPortImpl implements PostCommentsRepositoryPort {
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    public PostCommentsPortImpl(PostCommentsRepository postCommentsRepository) {
        this.postCommentsRepository = postCommentsRepository;
    }

    @Override
    public List<PostComment> getCommentsByPostId(int id) {
        return postCommentsRepository.findAllByPostId(id);
    }

    @Override
    public void save(PostComment postComment) {
        postCommentsRepository.save(postComment);
    }

    @Override
    public Optional<PostComment> findCommentByIdForPostWithId(Integer commentId, Integer postId) {
        return postCommentsRepository.findCommentByIdForPostWithId(commentId, postId);
    }
}
