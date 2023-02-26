package com.viksy.mobilele.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    private final String message;

    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
