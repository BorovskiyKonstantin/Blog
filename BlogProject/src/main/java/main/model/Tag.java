package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *      tags   -   тэги
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● name   VARCHAR(255)   NOT   NULL
 */

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;
}
