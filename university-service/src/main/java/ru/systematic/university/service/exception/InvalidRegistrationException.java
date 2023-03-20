package ru.systematic.university.service.exception;

public class InvalidRegistrationException extends RuntimeException {
    public InvalidRegistrationException() {
    }

    public InvalidRegistrationException(String message) {
        super(message);
    }
}
