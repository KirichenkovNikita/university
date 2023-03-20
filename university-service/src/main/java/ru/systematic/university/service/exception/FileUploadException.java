package ru.systematic.university.service.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException() {
    }

    public FileUploadException(String message) {
        super(message);
    }
}
