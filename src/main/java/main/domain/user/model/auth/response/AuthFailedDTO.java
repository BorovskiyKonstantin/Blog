package main.domain.user.model.auth.response;

public class AuthFailedDTO implements AuthResponse {
    private final boolean result = false;

    public boolean isResult() {
        return result;
    }
}
