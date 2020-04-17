package main.dao.postcomments;

import main.domain.postcomments.port.PostCommentsRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostCommentsPortImpl implements PostCommentsRepositoryPort {
    @Autowired
    PostCommentsRepository postCommentsRepository;

    @Override
    public Integer getCommentCountByPostId(int id) {
        return postCommentsRepository.getCommentCountByPostId(id);
    }
}
