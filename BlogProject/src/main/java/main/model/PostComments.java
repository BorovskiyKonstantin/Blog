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
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "parent_id")
    private int parentId;

    @NotNull
    @Column(name = "post_id")
    private int postId;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "time")
    private Timestamp time;

    //=============================================
    //Getters And Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
