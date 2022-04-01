package de.hse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CategoryException extends RuntimeException{

    private String message;
    public CategoryException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
