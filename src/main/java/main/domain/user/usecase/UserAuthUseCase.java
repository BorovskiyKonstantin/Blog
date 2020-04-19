package main.domain.user.usecase;

import main.domain.user.model.UserAuthCheckDTO;
import org.springframework.stereotype.Component;

@Component
public class UserAuthUseCase {
    public UserAuthCheckDTO userAuthCheck(){
        return new UserAuthCheckDTO();
    }
}
