package main.web.api;

import main.domain.captchacode.model.CaptchaCodeResponseDTO;
import main.domain.captchacode.usecase.CaptchaUseCase;
import main.domain.globalsetting.usecase.GlobalSettingUseCase;
import main.domain.user.model.auth.AuthResponseDTO;
import main.domain.user.model.changepass.ChangePassRequestDTO;
import main.domain.user.model.changepass.ChangePassResponseDTO;
import main.domain.user.model.register.RegisterRequestDTO;
import main.domain.user.model.register.RegisterResponseDTO;
import main.domain.user.model.restore.RestoreRequestDTO;
import main.domain.user.model.restore.RestoreResponseDTO;
import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * 1. + Вход   -   POST   /api/auth/login (осуществляется SpringSecurity)
 * 2. + Статус   авторизации   -   GET   /api/auth/check
 * 3. + Восстановление   пароля   -   POST   /api/auth/restore
 * 4. + Изменение   пароля   -   POST   /api/auth/password
 * 5. + Регистрация   -   POST   /api/auth/register
 * 6. + Капча   -   GET   /api/auth/captcha
 * 7. + Выход   -   GET   /api/auth/logout (осуществляется SpringSecurity)
 */

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private UserUseCase userUseCase;
    private CaptchaUseCase captchaUseCase;
    private GlobalSettingUseCase globalSettingUseCase;

    @Autowired
    public ApiAuthController(UserUseCase userUseCase, CaptchaUseCase captchaUseCase, GlobalSettingUseCase globalSettingUseCase) {
        this.userUseCase = userUseCase;
        this.captchaUseCase = captchaUseCase;
        this.globalSettingUseCase = globalSettingUseCase;
    }

    // 2. Статус   авторизации   -   GET   /api/auth/check
    @GetMapping(value = "/check")
    public AuthResponseDTO authCheck(Authentication authentication) {
        return userUseCase.authCheck(authentication);
    }

    // 3. Восстановление   пароля   -   POST   /api/auth/restore
    @PostMapping(value = "/restore")
    public RestoreResponseDTO restorePassword(@RequestBody RestoreRequestDTO requestDTO){
        return userUseCase.restorePassword(requestDTO.getEmail());
    }

    // 4. Изменение   пароля   -   POST   /api/auth/password
    @PostMapping(value = "/password")
    public ChangePassResponseDTO changePassword(@RequestBody ChangePassRequestDTO requestDTO){
        return userUseCase.changePassword(
                requestDTO.getCode(),
                requestDTO.getPassword(),
                requestDTO.getCaptcha(),
                requestDTO.getCaptchaSecret());
    }

    // 5. Регистрация   -   POST   /api/auth/register
    @PostMapping(value = "/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO requestDTO) {
        //Проверка режима глобальных настроек
        if(!globalSettingUseCase.isMultiuserModeEnabled()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return userUseCase.registerUser(
                requestDTO.getEmail(),
                requestDTO.getName(),
                requestDTO.getPassword(),
                requestDTO.getCaptcha(),
                requestDTO.getCaptchaSecret());
    }

    // 6. Капча   -   GET   /api/auth/captcha
    @GetMapping(value = "/captcha")
    public CaptchaCodeResponseDTO generateCaptcha(){
        return captchaUseCase.generateCaptcha();
    }
}
