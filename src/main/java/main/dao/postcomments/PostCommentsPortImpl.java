package main.dao.postcomments;

import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostCommentsPortImpl implements PostCommentsRepositoryPort {
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    public PostCommentsPortImpl(PostCommentsRepository postCommentsRepository) {
        this.postCommentsRepository = postCommentsRepository;
    }

    @Override
    public Integer getCommentCountByPostId(int id) {
        return postCommentsRepository.getCommentCountByPostId(id);
    }

    @Override
    public List<PostComment> getCommentsByPostId(int id) {
        return postCommentsRepository.findAllByPostId(id);
    }
}
