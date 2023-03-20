package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.StudentType;

import java.util.Arrays;

public class StudentTypeDaoTest extends AbstractDaoTest<StudentType> {

    @Autowired
    private StudentTypeDao dao;

    @Override
    void setData() {
        StudentType studentType1 = new StudentType();
        studentType1.setId(1L);
        studentType1.setName("Очное обучение");

        StudentType studentType2 = new StudentType();
        studentType2.setName("Заочное обучение");
        studentType2.setId(2L);

        super.dao = this.dao;
        super.entities = Arrays.asList(studentType1, studentType2);
    }
}
