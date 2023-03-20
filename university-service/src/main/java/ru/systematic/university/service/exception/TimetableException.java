package ru.systematic.university.service.exception;

public class TimetableException extends RuntimeException {
    public TimetableException() {
    }

    public TimetableException(String message) {
        super(message);
    }
}
