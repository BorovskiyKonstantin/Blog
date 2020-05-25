package main.domain.user.entity;

import lombok.Data;
import main.domain.post.entity.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 *      users   -   пользователи
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● is_moderator   TINYINT   NOT   NULL   -   является   ли   пользователь   модератором  (может   ли   править   глобальные   настройки   сайта   и   модерировать   посты)
 *       ● reg_time   DATETIME   NOT   NULL   -   дата   и   время   регистрации   пользователя
 *       ● name   VARCHAR(255)   NOT   NULL   -   имя   пользователя
 *       ● email   VARCHAR(255)   NOT   NULL   -   e-mail   пользователя
 *       ● password   VARCHAR(255)   NOT   NULL   -   хэш   пароля   пользователя
 *       ● code   VARCHAR(255)   -   код   для   восстановления   пароля,   может   быть   NULL
 *       ● photo   TEXT   -   фотография   (ссылка   на   файл),   может   быть   NULL
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_moderator", columnDefinition = "TINYINT", nullable = false)
    private boolean isModerator;

    @Column(name = "reg_time", nullable = false)
    private Timestamp regTime;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "code")
    private String code;

    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @OneToMany(mappedBy = "moderator", fetch = FetchType.EAGER)
    private List<Post> moderatedPosts;
}
