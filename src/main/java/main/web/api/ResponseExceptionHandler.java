package main.web.api;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
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

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    private ResponseEntity<Object> maxSizeLimitException(MaxUploadSizeExceededException ex) {
        Map<String, Object> errorDTO = new LinkedHashMap<>();
        errorDTO.put("result", false);
        if(ex.getCause().getCause() instanceof FileSizeLimitExceededException)
            errorDTO.put("errors", Collections.singletonMap("file", "Размер файла превышает допустимый размер"));
        if(ex.getCause().getCause() instanceof SizeLimitExceededException)
            errorDTO.put("errors", Collections.singletonMap("request", "Размер запроса превышает допустимый размер"));
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

}

