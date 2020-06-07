package main.domain.postvote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import main.domain.post.entity.Post;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * post_votes     -   лайки   и   дизлайки   постов
 * ● id   INT   NOT   NULL   AUTO_INCREMENT
 * ?● user_id   INT   NOT   NULL   -   тот,   кто   поставил   лайк   /   дизлайк
 * ?● post_id   INT   NOT   NULL   -   пост,   которому   поставлен   лайк   /   дизлайк
 * ● time   DATETIME   NOT   NULL   -   дата   и   время  л айка   /   дизлайка
 * ● value   TINYINT   NOT   NULL   -   лайк   или   дизлайк:   1   или   -1
 */
@Data
@Entity
@Table(name = "post_votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "value", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean value;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;
}
