package ru.systematic.university.service.validator;

public interface EntityValidator<E> {
    void validate(E entity);
}
