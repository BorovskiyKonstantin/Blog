package main.domain.postvote.model;

public class VoteResponseDTO {
    private boolean result;

    public VoteResponseDTO(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
