package ru.systematic.university.service.user;

import ru.systematic.university.domain.Student;

public class StudentRegistrationRequest extends UserRegistrationRequest<Student> {
    @Override
    public Student getEntity() {
        Student student = new Student();
        student.setLogin(getLogin());
        student.setPassword(getPassword());
        student.setFirstName(getFirstName());
        student.setLastName(getLastName());

        return student;
    }
}
