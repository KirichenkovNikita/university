package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Professor;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfessorDaoTest extends AbstractDaoTest<Professor>{

    @Autowired
    private ProfessorDao dao;

    @Override
    void setData() {
        Professor professor1 = new Professor();
        professor1.setId(1L);
        professor1.setLinkedin("linkedin.com");
        professor1.setLogin("login");
        professor1.setPassword("password");
        professor1.setFirstName("Ivan");
        professor1.setLastName("Ivanov");
        professor1.setAvatar("/1");
        professor1.setAvatarApproved(false);
        professor1.setCourses(new ArrayList<>());

        Professor professor2 = new Professor();
        professor2.setId(2L);
        professor2.setLinkedin("linkedin.com");
        professor2.setLogin("login");
        professor2.setPassword("password");
        professor2.setFirstName("Ivan");
        professor2.setLastName("Ivanov");
        professor2.setAvatar("/2");
        professor2.setAvatarApproved(false);
        professor2.setCourses(new ArrayList<>());

        super.entities = Arrays.asList(professor1, professor2);
        super.dao = this.dao;
    }
}
