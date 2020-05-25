package main.domain.captchacode.entity;

import lombok.Data;

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

@Data
@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "code", columnDefinition = "TINYTEXT", nullable = false)
    private String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT", nullable = false)
    private String secretCode;
}
