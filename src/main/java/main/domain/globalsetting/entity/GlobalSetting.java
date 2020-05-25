package main.domain.globalsetting.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *      global_settings   -   глобальные   настройки   движка
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● code   VARCHAR(255)   NOT   NULL   -   системное   имя   настройки
 *       ● name   VARCHAR(255)   NOT   NULL   -   название   настройки
 *       ● value   VARCHAR(255)   NOT   NULL   -   значение   настройки
 */

@Data
@Entity
@Table(name = "global_settings")
public class GlobalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

}
