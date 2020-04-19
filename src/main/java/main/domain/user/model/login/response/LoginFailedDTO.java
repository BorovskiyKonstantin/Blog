package main.domain.user.model.login.response;

public class LoginFailedDTO implements LoginResponse {
    private final boolean result = false;

    public boolean isResult() {
        return result;
    }
}
