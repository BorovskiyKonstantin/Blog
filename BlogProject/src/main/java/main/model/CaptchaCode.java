package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *      captcha_codes   -    коды   капч
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● time   DATETIME   NOT   NULL   -   дата   и   время  г енерации   кода   капчи
 *       ● code   TINYTEXT   NOT   NULL   -   код,   отображаемый   на   картинкке   капчи
 *       ● secret_code   TINYTEXT   NOT   NULL   -   код,   передаваемый   в   параметре
 */
@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private Timestamp time;

    @Column(columnDefinition = "TINYTEXT")
    @NotNull
    private String code;

    @Column(columnDefinition = "TINYTEXT")
    @NotNull
    private String secretCode;
}
