package main.domain.captchacode.port;

import main.domain.captchacode.entity.CaptchaCode;

import java.util.Optional;

public interface CaptchaCodeRepositoryPort {
    Optional<CaptchaCode> findBySecretCode(String secretCode);
    void save(CaptchaCode captcha);
}
