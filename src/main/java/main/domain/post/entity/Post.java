package main.domain.post.entity;

import main.domain.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *      posts   -   посты
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● is_active   TINYINT   NOT   NULL   -   скрыта   или   активна   публикация:   0   или   1
 *       ● moderation_status   ENUM(‘NEW’,   ‘ACCEPTED’,   ‘DECLINED’)   NOT   NULL   -   статус  модерации,   по   умолчанию   значение   “NEW”.
 *       ● moderator_id   INT   -   ID   пользователя-модератора,   принявшего   решение,   или   NULL
 *       ● user_id   INT   NOT   NULL   -   автор   поста
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  п убликации   поста
 *       ● title   VARCHAR(255)   NOT   NULL   -   заголовок   поста
 *       ● text   TEXT   NOT   NULL   -   текст   поста
 *       ● view_count   INT   NOT   NULL   -   количество   просмотров   поста
 * */
@Entity(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "is_active",columnDefinition = "TINYINT")
    private boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "VARCHAR(255) default 'NEW'")
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "time")
    private Timestamp time;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @NotNull
    @Column(name = "view_count")
    private int viewCount;

    //=============================================
    //Getters And Setters


    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getViewCount() {
        return viewCount;
    }
}
