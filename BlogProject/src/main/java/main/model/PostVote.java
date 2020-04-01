package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *      post_votes     -   лайки   и   дизлайки   постов
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ?● user_id   INT   NOT   NULL   -   тот,   кто   поставил   лайк   /   дизлайк
 *       ?● post_id   INT   NOT   NULL   -   пост,   которому   поставлен   лайк   /   дизлайк
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  л айка   /   дизлайка
 *       ● value   TINYINT   NOT   NULL   -   лайк   или   дизлайк:   1   или   -1
 */

@Entity
@Table(name = "post_votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    @Column(name = "post_id")
    @NotNull
    private int postId;

    @NotNull
    private Timestamp time;

    @Column(columnDefinition = "TINYINT")
    @NotNull
    private boolean value;
}
