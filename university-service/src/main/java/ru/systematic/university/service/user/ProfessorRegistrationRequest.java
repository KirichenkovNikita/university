package ru.systematic.university.service.user;

import ru.systematic.university.domain.Professor;

public class ProfessorRegistrationRequest extends UserRegistrationRequest<Professor> {
    @Override
    public Professor getEntity() {
        Professor professor = new Professor();
        professor.setLogin(getLogin());
        professor.setPassword(getPassword());
        professor.setFirstName(getFirstName());
        professor.setLastName(getLastName());

        return professor;
    }
}
