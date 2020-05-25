package main.domain.user.usecase;

import main.domain.captchacode.entity.CaptchaCode;
import main.domain.captchacode.port.CaptchaCodeRepositoryPort;
import main.domain.user.entity.User;
import main.domain.user.model.auth.AuthResponseDTO;
import main.domain.user.model.register.RegisterResponseDTO;
import main.domain.user.port.UserRepositoryPort;
import main.web.security.user.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserUseCase {
    @Autowired
    UserRepositoryPort userRepositoryPort;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CaptchaCodeRepositoryPort captchaCodeRepositoryPort;

    public Optional<User> getUserByUsername(String username) {
        return userRepositoryPort.findUserByName(username);
    }

    public Optional<User> getUserById(int id) {
        return userRepositoryPort.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepositoryPort.findUserByEmail(email);
    }

    public RegisterResponseDTO registerUser(String email, String name, String password, String captcha, String captchaSecret) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (userRepositoryPort.findUserByEmail(email).isPresent())
            errors.put("email", "Этот e-mail уже зарегистрирован");

        if (name == null || name.matches(".*\\d+.*"))
            errors.put("name", "Имя указано неверно");

        if (password.length() < 6)
            errors.put("password", "Пароль короче 6-ти символов");

        CaptchaCode captchaCode = captchaCodeRepositoryPort.findBySecretCode(captchaSecret).orElseThrow();
        if (!captchaCode.getCode().equals(captcha))
            errors.put("captcha", "Код картинки введен неверно");

        //Формирование ответа при наличии ошибок
        if (errors.size() != 0)
            return new RegisterResponseDTO(false, errors);

        else {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setModerator(false);
            user.setRegTime(new Timestamp(System.currentTimeMillis()));
            userRepositoryPort.save(user);
            return new RegisterResponseDTO(true, null);
        }
    }

    public AuthResponseDTO authCheck(Authentication authentication) {
        if (authentication == null) {
            return AuthResponseDTO.failed();
        } else {
            WebUser webUser = (WebUser) authentication.getPrincipal();
            String email = webUser.getUsername();
            User user = userRepositoryPort.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
            return AuthResponseDTO.successfulLogIn(user);
        }
    }
}
