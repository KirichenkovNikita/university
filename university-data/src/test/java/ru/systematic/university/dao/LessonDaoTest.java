package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Lesson;

import java.time.LocalDateTime;
import java.util.Arrays;

public class LessonDaoTest extends AbstractDaoTest<Lesson>{

    @Autowired
    private LessonDao dao;

    @Override
    void setData() {
        LocalDateTime time = LocalDateTime.of(2020, 12, 12, 12, 12);
        LocalDateTime timeAfter = LocalDateTime.of(2020, 12, 12, 12, 13);

        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setCourseId(1L);
        lesson1.setStartTime(time);
        lesson1.setEndTime(timeAfter);

        Lesson lesson2 = new Lesson();
        lesson2.setId(2L);
        lesson2.setCourseId(1L);
        lesson2.setStartTime(time);
        lesson2.setEndTime(timeAfter);

        super.entities = Arrays.asList(lesson1, lesson2);
        super.dao = this.dao;
    }
}
