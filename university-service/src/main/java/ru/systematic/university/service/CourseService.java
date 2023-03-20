package ru.systematic.university.service;

import org.springframework.data.domain.Pageable;
import ru.systematic.university.domain.Course;

import java.util.List;

public interface CourseService {
    void add(Course course);

    List<Course> findAll();

    List<Course> findAll(Pageable pageable);

    void update(Course course);

    void deleteById(Long id);
}
