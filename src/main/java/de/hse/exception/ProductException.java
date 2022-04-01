package de.hse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ProductException extends RuntimeException{
    public ProductException(String message) {
        super(message);
    }
}
