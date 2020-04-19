package main.web.api;

//обрабатывает все запросы /api/auth/*

import main.domain.user.model.UserAuthCheckDTO;
import main.domain.user.model.login.request.UserLoginRequestDTO;
import main.domain.user.model.login.response.LoginFailedDTO;
import main.domain.user.model.login.response.LoginResponse;
import main.domain.user.usecase.UserAuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. -(заглушка) Вход   -   POST   /api/auth/login
 * 2. -(заглушка) Статус   авторизации   -   GET   /api/auth/check
 * 3. ? Восстановление   пароля   -   POST   /api/auth/restore
 * 4. ? Изменение   пароля   -   POST   /api/auth/password
 * 5. ? Регистрация   -   POST   /api/auth/register
 * 6. ? Капча   -   GET   /api/auth/captcha
 * 7.   Выход   -   GET   /api/auth/logout
 * */

@RestController
public class ApiAuthController {
    @Autowired
    UserAuthUseCase userAuthUseCase;

    //TODO: Заглушка! Реализовать в будущем
//    1. Вход   -   POST   /api/auth/login
    @PostMapping(value = "/api/auth/login")
    public LoginResponse login(@RequestBody UserLoginRequestDTO loginDTO){
//        return new LoginSuccessfulDTO(1,"name","photo","email",true,10,true);
        return new LoginFailedDTO();
    }

    //TODO: Заглушка для главной страницы! Реализовать в будущем
//    2. Статус   авторизации   -   GET   /api/auth/check
    @GetMapping (value = "/api/auth/check")
    public UserAuthCheckDTO authCheck(){
        return userAuthUseCase.userAuthCheck();
    }

}
