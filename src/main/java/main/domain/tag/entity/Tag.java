package main.domain.tag.entity;

import lombok.Data;
import main.domain.post.entity.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *      tags   -   тэги
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● name   VARCHAR(255)   NOT   NULL
 */
@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public Tag() {
    }

    public Tag(String name){
        this.name = name;
    }
}
