package main.web.api;

//обрабатывает все запросы /api/auth/*

import main.domain.user.entity.User;
import main.domain.user.model.auth.response.AuthResponseDTO;
import main.domain.user.usecase.UserUseCase;
import main.security.user.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    UserUseCase userUseCase;

    //    2. Статус   авторизации   -   GET   /api/auth/check
    @GetMapping(value = "/api/auth/check")
    public AuthResponseDTO authCheck(Authentication authentication) {
        if (authentication == null) {
            return AuthResponseDTO.failed();
        }
        else {
            WebUser webUser = (WebUser) authentication.getPrincipal();
            String email = webUser.getUsername();
            User user = userUseCase.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
            return AuthResponseDTO.successfulLogIn(user);
        }
    }
}
