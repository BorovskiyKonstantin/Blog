package main.domain.user.entity;

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
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "is_moderator", columnDefinition = "TINYINT")
    private boolean isModerator;

    @NotNull
    @Column(name = "reg_time")
    private Timestamp regTime;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "code")
    private String code;

    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    @OneToMany(mappedBy = "moderator")
    private List<Post> moderatedPosts;

    //=============================================
    //Getters And Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Post> getModeratedPosts() {
        return moderatedPosts;
    }

    public void setModeratedPosts(List<Post> moderatedPosts) {
        this.moderatedPosts = moderatedPosts;
    }
}
