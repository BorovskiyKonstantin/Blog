package main.model;

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
    private int id;

    @Column(name = "is_active",columnDefinition = "TINYINT")
    @NotNull
    private byte isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "VARCHAR(255) default 'NEW'")
    @NotNull
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @Column(name = "moderator_id")
    private int moderatorId;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    @NotNull
    private Timestamp time;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String text;        //Как сделать, чтобы при создании был тип TEXT?

    @Column(name = "view_count")
    @NotNull
    private int viewCount;


}
