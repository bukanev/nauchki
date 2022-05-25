package com.example.nauchki.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> badToken(JWTVerificationException e){
        log.error("Bad token: {}", e.getMessage());
        return new ResponseEntity<>("Bad token: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> notFoundException(ResourceNotFoundException e){
        log.error("Resource not found, {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> catchOtherException(Exception e){
        log.error("Exception, {}", e.getMessage() + "\n" + e.getStackTrace()[0]);
        return new ResponseEntity<>(e.getMessage() + "\n" + e.getStackTrace()[0], HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
