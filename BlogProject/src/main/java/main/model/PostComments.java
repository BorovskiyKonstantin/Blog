package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *      post_comments   -  к омментарии   к   постам
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ?● parent_id   INT   -   комментарий,   на   который  о ставлен   этот   комментарий   (может  быть   NULL,   если   комментарий   оставлен   просто   к   посту)
 *       ?● post_id   INT   NOT   NULL   -   пост,   к   которому  н аписан   комментарий
 *       ?● user_id   INT   NOT   NULL   -   автор   комментария
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  к омментария
 */
@Entity
@Table(name = "post_comments")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "parent_id")
    @NotNull
    private int parentId;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    @NotNull
    private Timestamp time;
}
