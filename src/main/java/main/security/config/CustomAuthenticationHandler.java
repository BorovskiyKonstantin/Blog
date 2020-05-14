package main.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.domain.user.entity.User;
import main.domain.user.model.auth.response.AuthResponseDTO;
import main.security.user.model.WebUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import main.domain.user.usecase.UserUseCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationHandler
        implements AuthenticationFailureHandler, AuthenticationSuccessHandler,
        LogoutSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserUseCase userUseCase;

    public CustomAuthenticationHandler(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        objectMapper.writeValue(response.getOutputStream(), AuthResponseDTO.failed());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        WebUser webUser = WebUser.fromAuth(authentication);

        User loggedInUser = userUseCase.getUserById(webUser.getId()).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + webUser.getUsername())
        );

        objectMapper.writeValue(response.getOutputStream(), AuthResponseDTO.successfulLogIn(loggedInUser));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        objectMapper.writeValue(response.getOutputStream(), AuthResponseDTO.logOut());
    }
}
