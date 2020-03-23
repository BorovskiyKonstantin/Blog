package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *      global_settings   -   глобальные   настройки   движка
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● code   VARCHAR(255)   NOT   NULL   -   системное   имя   настройки
 *       ● name   VARCHAR(255)   NOT   NULL   -   название   настройки
 *       ● value   VARCHAR(255)   NOT   NULL   -   значение   настройки
 */

@Entity
@Table(name = "global_settings")
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String value;
}
