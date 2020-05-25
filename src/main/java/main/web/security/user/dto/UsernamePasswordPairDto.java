package main.web.security.user.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class UsernamePasswordPairDto {
    private String email;
    private String password;

    public UsernamePasswordPairDto() {
    }

    public UsernamePasswordPairDto(String email, String password) {
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
