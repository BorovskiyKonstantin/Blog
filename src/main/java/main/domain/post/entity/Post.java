package main.domain.post.entity;

import lombok.Data;
import main.domain.postcomments.entity.PostComment;
import main.domain.tag.entity.Tag;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * posts   -   посты
 * ● id   INT   NOT   NULL   AUTO_INCREMENT
 * ● is_active   TINYINT   NOT   NULL   -   скрыта   или   активна   публикация:   0   или   1
 * ● moderation_status   ENUM(‘NEW’,   ‘ACCEPTED’,   ‘DECLINED’)   NOT   NULL   -   статус  модерации,   по   умолчанию   значение   “NEW”.
 * ● moderator_id   INT   -   ID   пользователя-модератора,   принявшего   решение,   или   NULL
 * ● user_id   INT   NOT   NULL   -   автор   поста
 * ● time   DATETIME   NOT   NULL   -   дата   и   время  п убликации   поста
 * ● title   VARCHAR(255)   NOT   NULL   -   заголовок   поста
 * ● text   TEXT   NOT   NULL   -   текст   поста
 * ● view_count   INT   NOT   NULL   -   количество   просмотров   поста
 */
@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_active", columnDefinition = "TINYINT", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "VARCHAR(255) default 'NEW'", nullable = false)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostComment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

}
