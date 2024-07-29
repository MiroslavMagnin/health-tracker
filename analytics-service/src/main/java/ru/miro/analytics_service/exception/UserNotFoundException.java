package ru.miro.analytics_service.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("The user with this id=" + id + " is not found");
    }
    public UserNotFoundException(String email) {
        super("The user with this email=" + email + " is not found");
    }

}
