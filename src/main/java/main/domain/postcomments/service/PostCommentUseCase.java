package main.domain.postcomments.service;

import main.domain.post.entity.Post;
import main.domain.post.port.PostRepositoryPort;
import main.domain.postcomments.entity.PostComment;
import main.domain.postcomments.model.CommentRequestDTO;
import main.domain.postcomments.model.CommentResponseDTO;
import main.domain.postcomments.port.PostCommentsRepositoryPort;
import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PostCommentUseCase {
    private PostCommentsRepositoryPort postCommentsRepositoryPort;
    private UserUseCase userUseCase;
    private PostRepositoryPort postRepositoryPort;

    @Autowired
    public PostCommentUseCase(PostCommentsRepositoryPort postCommentsRepositoryPort, UserUseCase userUseCase, PostRepositoryPort postRepositoryPort) {
        this.postCommentsRepositoryPort = postCommentsRepositoryPort;
        this.userUseCase = userUseCase;
        this.postRepositoryPort = postRepositoryPort;
    }

    public CommentResponseDTO saveComment(CommentRequestDTO commentRequestDTO) throws Exception{
        Integer parentId = commentRequestDTO.getParentId();
        Integer postId = commentRequestDTO.getPostId();
        String text = commentRequestDTO.getText();

        if (text.length() < 2) {
            Map<String,String> errors = new LinkedHashMap<>();
            errors.put("text","Текст комментария не задан или слишком короткий");
            return new CommentResponseDTO(errors);
        }

        //Бросить exception, если не найден активный пост с переданным id
        postRepositoryPort.getPostById(postId).orElseThrow();
        //Бросить exception, если у поста не найден комментарий с id = parentId
        if (parentId != null) {
            postCommentsRepositoryPort.findCommentByIdForPostWithId(parentId, postId).orElseThrow();
        }

        int userId = userUseCase.getCurrentUser().getId();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        PostComment postComment = new PostComment(parentId, postId, userId, currentTime, text);
        postCommentsRepositoryPort.save(postComment);

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(postComment.getId());
        return responseDTO;
    }

}
