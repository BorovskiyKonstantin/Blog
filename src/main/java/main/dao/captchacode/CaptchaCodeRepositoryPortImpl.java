package main.dao.captchacode;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CaptchaCodeRepositoryPortImpl implements CaptchaCodeRepositoryPort {
    @Autowired
    CaptchaCodeRepository captchaCodeRepository;

    @Override
    public Optional<CaptchaCode> findBySecretCode(String secretCode) {
        return captchaCodeRepository.findBySecretCodeLike(secretCode);
    }

    @Override
    public void save(CaptchaCode captcha) {
        captchaCodeRepository.save(captcha);
    }
}
