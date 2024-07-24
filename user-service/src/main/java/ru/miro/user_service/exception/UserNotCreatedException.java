package ru.miro.user_service.exception;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException (String message) {
        super(message);
    }
}
