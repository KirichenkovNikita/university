package ru.systematic.university.service.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
