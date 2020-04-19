package main.dao.captchacode;

import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaptchaCodeRepositoryPortImpl implements CaptchaCodeRepositoryPort {
    @Autowired
    CaptchaCodeRepository captchaCodeRepository;
}
