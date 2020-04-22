package main.domain.user.model.auth.request;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AuthRequestDTO {
    private String email;
    private String password;

    public AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @JsonSetter(value = "e_mail")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
