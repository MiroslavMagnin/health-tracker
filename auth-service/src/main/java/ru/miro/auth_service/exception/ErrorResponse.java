package ru.miro.auth_service.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public ErrorResponse(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
