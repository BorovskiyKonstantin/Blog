package main.domain.globalsetting.entity;

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
public class GlobalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "value")
    private String value;

    //=============================================
    //Getters And Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
