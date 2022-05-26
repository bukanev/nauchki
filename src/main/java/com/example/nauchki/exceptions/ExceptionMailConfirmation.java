package com.example.nauchki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExceptionMailConfirmation extends RuntimeException {
    public ExceptionMailConfirmation(String msg) {
        super(msg);
    }

    public ExceptionMailConfirmation(String msg, Throwable cause) {
        super(msg, cause);
    }
}

