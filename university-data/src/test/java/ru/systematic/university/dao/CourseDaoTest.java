package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Course;

import java.util.Arrays;

public class CourseDaoTest extends AbstractDaoTest<Course>{

    @Autowired
    private CourseDao dao;

    @Override
    void setData() {
        Course entity1 = new Course();
        entity1.setId(1L);
        entity1.setName("Химия");
        entity1.setDescription("Органическая химия");

        Course entity2 = new Course();
        entity2.setId(2L);
        entity2.setName("Физика");
        entity2.setDescription("Оптика");

        entities = Arrays.asList(entity1, entity2);
        super.dao = this.dao;
    }
}
