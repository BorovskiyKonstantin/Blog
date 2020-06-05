package main.dao.captchacode;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class CaptchaCodeRepositoryPortImpl implements CaptchaCodeRepositoryPort {
    private CaptchaCodeRepository captchaCodeRepository;

    @Autowired
    public CaptchaCodeRepositoryPortImpl(CaptchaCodeRepository captchaCodeRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
    }

    @Override
    public void deleteOld(Timestamp currentTime, Timestamp captchaDurationTime) {
        captchaCodeRepository.deleteOld(currentTime, captchaDurationTime);
    }

    @Override
    public Optional<CaptchaCode> findBySecretCode(String secretCode) {
        return captchaCodeRepository.findBySecretCodeLike(secretCode);
    }

    @Override
    public void save(CaptchaCode captcha) {

        captchaCodeRepository.save(captcha);
    }
}
