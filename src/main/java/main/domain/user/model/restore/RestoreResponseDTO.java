package main.domain.user.model.restore;

public class RestoreResponseDTO {
    private boolean result;

    public RestoreResponseDTO(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
