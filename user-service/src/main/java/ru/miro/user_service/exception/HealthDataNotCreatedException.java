package ru.miro.user_service.exception;

public class HealthDataNotCreatedException extends RuntimeException {

    public HealthDataNotCreatedException(String message) {
        super(message);
    }
}
