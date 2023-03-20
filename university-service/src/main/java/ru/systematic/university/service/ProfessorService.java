package ru.systematic.university.service;

import ru.systematic.university.domain.Professor;
import ru.systematic.university.service.user.ProfessorRegistrationRequest;

public interface ProfessorService extends UserService<Professor> {
    Professor register(ProfessorRegistrationRequest request);
}
