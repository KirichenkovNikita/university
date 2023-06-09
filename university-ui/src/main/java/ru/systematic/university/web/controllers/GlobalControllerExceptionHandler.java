package ru.systematic.university.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String handleGlobalError() {
        return "error/mainerror";
    }
}
