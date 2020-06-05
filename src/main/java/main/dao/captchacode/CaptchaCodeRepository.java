package main.dao.captchacode;

import main.domain.captchacode.entity.CaptchaCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
    Optional<CaptchaCode> findBySecretCodeLike(String secretCode);

    @Query(value = "SELECT * FROM captcha_codes cc WHERE ", nativeQuery = true)
    void deleteOld(Timestamp currentTime, Timestamp captchaDurationTime);
}
