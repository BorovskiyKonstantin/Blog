package main.domain.user.model.register;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponseDTO {
    private boolean result;
    private Map<String, String> errors;

    public RegisterResponseDTO(boolean result, Map<String, String> errors) {
        this.result = result;
        this.errors = errors;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

