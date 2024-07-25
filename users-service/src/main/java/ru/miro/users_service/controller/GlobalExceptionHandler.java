package ru.miro.users_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.miro.users_service.exception.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchUserNotCreatedException(UserNotCreatedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchHealthDataNotFoundException(HealthDataNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchHealthDataNotCreatedException(HealthDataNotCreatedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_ACCEPTABLE);
    }

}
