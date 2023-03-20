package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Student;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentDaoTest extends AbstractDaoTest<Student>{

    @Autowired
    private StudentDao dao;

    @Override
    void setData() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setLogin("login");
        student1.setPassword("password");
        student1.setFirstName("Ivan");
        student1.setLastName("Ivanov");
        student1.setAvatar("/1.png");
        student1.setAvatarApproved(false);
        student1.setCourses(new ArrayList<>());
        student1.setGroupId(1L);
        student1.setType(1L);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setLogin("login");
        student2.setPassword("password");
        student2.setFirstName("Ivan");
        student2.setLastName("Ivanov");
        student2.setAvatar("/2.png");
        student2.setAvatarApproved(false);
        student2.setCourses(new ArrayList<>());
        student2.setGroupId(1L);
        student2.setType(1L);

        super.entities = Arrays.asList(student1, student2);
        super.dao = this.dao;
    }
}
