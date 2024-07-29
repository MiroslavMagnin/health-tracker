package ru.miro.analytics_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    private String message;
    private long timestamp;
    
}
