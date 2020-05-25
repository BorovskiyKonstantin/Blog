package main.web.api;

import main.domain.captchacode.model.CaptchaCodeResponseDTO;
import main.domain.captchacode.usecase.CaptchaUseCase;
import main.domain.user.entity.User;
import main.domain.user.model.auth.AuthResponseDTO;
import main.domain.user.model.register.RegisterRequestDTO;
import main.domain.user.model.register.RegisterResponseDTO;
import main.domain.user.usecase.UserUseCase;
import main.web.security.user.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * 1. + Вход   -   POST   /api/auth/login (осуществляется SpringSecurity)
 * 2. + Статус   авторизации   -   GET   /api/auth/check
 * 3. ? Восстановление   пароля   -   POST   /api/auth/restore
 * 4. ? Изменение   пароля   -   POST   /api/auth/password
 * 5. + Регистрация   -   POST   /api/auth/register
 * 6. + Капча   -   GET   /api/auth/captcha
 * 7. + Выход   -   GET   /api/auth/logout (осуществляется SpringSecurity)
 */

@RestController
public class ApiAuthController {
    @Autowired
    UserUseCase userUseCase;

    @Autowired
    CaptchaUseCase captchaUseCase;

    // 2. Статус   авторизации   -   GET   /api/auth/check
    @GetMapping(value = "/api/auth/check")
    public AuthResponseDTO authCheck(Authentication authentication) {
        return userUseCase.authCheck(authentication);
    }

    // 5. Регистрация   -   POST   /api/auth/register
    @PostMapping(value = "/api/auth/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO requestDTO) {
        return userUseCase.registerUser(
                requestDTO.getEmail(),
                requestDTO.getName(),
                requestDTO.getPassword(),
                requestDTO.getCaptcha(),
                requestDTO.getCaptchaSecret());
    }

    // 6. Капча   -   GET   /api/auth/captcha
    @GetMapping(value = "/api/auth/captcha")
    public CaptchaCodeResponseDTO generateCaptcha(){
        return captchaUseCase.generateCaptcha();
    }
}
