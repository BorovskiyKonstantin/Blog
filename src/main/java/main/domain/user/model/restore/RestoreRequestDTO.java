package main.domain.user.model.restore;

public class RestoreRequestDTO {
    private String email;

    public RestoreRequestDTO() {
    }

    public RestoreRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
