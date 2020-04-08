package main.domain.captchacode.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * captcha_codes   -    коды   капч
 * ● id   INT   NOT   NULL   AUTO_INCREMENT
 * ● time   DATETIME   NOT   NULL   -   дата   и   время  г енерации   кода   капчи
 * ● code   TINYTEXT   NOT   NULL   -   код,   отображаемый   на   картинкке   капчи
 * ● secret_code   TINYTEXT   NOT   NULL   -   код,   передаваемый   в   параметре
 */
@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "time")
    private Timestamp time;

    @NotNull
    @Column(name = "code", columnDefinition = "TINYTEXT")
    private String code;

    @NotNull
    @Column(name = "secret", columnDefinition = "TINYTEXT")
    private String secretCode;

    //=============================================
    //Getters And Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}
