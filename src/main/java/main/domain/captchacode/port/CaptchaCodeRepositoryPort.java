package main.domain.captchacode.port;

import main.domain.captchacode.entity.CaptchaCode;

import java.sql.Timestamp;
import java.util.Optional;

public interface CaptchaCodeRepositoryPort {
    void deleteOld(Timestamp currentTime, Timestamp captchaDurationTime);
    Optional<CaptchaCode> findBySecretCode(String secretCode);
    void save(CaptchaCode captcha);
}
