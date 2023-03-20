package ru.systematic.university.service;

import ru.systematic.university.domain.StudentType;

import java.util.List;

public interface StudentTypeService {
    void add(StudentType type);

    List<StudentType> findAll();

    void update(StudentType studentType);

    void deleteById(Long id);
}
