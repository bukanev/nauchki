package com.example.nauchki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DeniedException extends RuntimeException {

    public DeniedException(String msg) {
        super(msg);
    }

    public DeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

