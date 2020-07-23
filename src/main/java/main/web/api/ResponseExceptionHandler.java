package main.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ResponseStatusException.class})
    private ResponseEntity<Object> statusExceptionHandler(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getStatus());
    }

    @ExceptionHandler({NoSuchElementException.class})
    private ResponseEntity<Object> notFoundHandler() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

