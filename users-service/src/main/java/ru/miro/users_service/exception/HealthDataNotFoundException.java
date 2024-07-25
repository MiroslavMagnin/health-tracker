package ru.miro.users_service.exception;

public class HealthDataNotFoundException extends RuntimeException {

    public HealthDataNotFoundException(long id) {
        super("The health data with this id=" + id + " isn't found");
    }
}
