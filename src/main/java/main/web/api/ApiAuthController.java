package main.web.api;

//обрабатывает все запросы /api/auth/*

import main.domain.user.model.auth.request.AuthRequestDTO;
import main.domain.user.model.auth.response.AuthResponse;
import main.domain.user.usecase.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 1. +(заглушка) Вход   -   POST   /api/auth/login
 * 2. -(заглушка) Статус   авторизации   -   GET   /api/auth/check
 * 3. ? Восстановление   пароля   -   POST   /api/auth/restore
 * 4. ? Изменение   пароля   -   POST   /api/auth/password
 * 5. ? Регистрация   -   POST   /api/auth/register
 * 6. ? Капча   -   GET   /api/auth/captcha
 * 7.   Выход   -   GET   /api/auth/logout
 */

@RestController
public class ApiAuthController {
    @Autowired
    UserSecurity userSecurity;

//    1. Вход   -   POST   /api/auth/login
    @PostMapping(value = "/api/auth/login")
    public AuthResponse login(HttpServletRequest request, @RequestBody AuthRequestDTO loginDTO) {
        AuthResponse responseDTO = userSecurity.loginUser(request, loginDTO);
        return responseDTO;
    }

    //TODO: Заглушка для главной страницы! Реализовать в будущем
//    2. Статус   авторизации   -   GET   /api/auth/check
    @GetMapping(value = "/api/auth/check")
    public AuthResponse authCheck(HttpServletRequest request) {
        return userSecurity.checkAuth(request);
    }

}
