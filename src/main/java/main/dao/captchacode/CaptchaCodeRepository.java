package main.dao.captchacode;

import main.domain.captchacode.entity.CaptchaCode;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
    Optional<CaptchaCode> findBySecretCodeLike(String secretCode);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM captcha_codes cc WHERE cc.time < ?1 ", nativeQuery = true)
    int deleteOld(Timestamp expiredTime);
}
