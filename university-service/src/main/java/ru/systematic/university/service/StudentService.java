package ru.systematic.university.service;

import ru.systematic.university.domain.Student;
import ru.systematic.university.service.user.StudentRegistrationRequest;

public interface StudentService extends UserService<Student> {
    Student register(StudentRegistrationRequest request);
}
