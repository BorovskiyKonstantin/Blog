package main.dao.captchacode;

import main.domain.captchacode.entity.CaptchaCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
    Optional<CaptchaCode> findBySecretCodeLike(String secretCode);
}
