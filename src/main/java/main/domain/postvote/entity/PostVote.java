package main.domain.postvote.entity;

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

@Entity
@Table(name = "post_votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "post_id")
    private int postId;

    @NotNull
    @Column(name = "time")
    private Timestamp time;

    @NotNull
    @Column(name = "value", columnDefinition = "TINYINT")
    private boolean value;

    //=============================================
    //Getters And Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
