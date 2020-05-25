package main.domain.tag.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
