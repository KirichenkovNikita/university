package ru.systematic.university.service;

import org.springframework.data.domain.Pageable;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.service.domain.LessonReport;
import ru.systematic.university.service.domain.week.LessonWeekTableRow;

import java.util.List;

public interface LessonService {
    void add(Lesson lesson);

    List<Lesson> findAll();

    List<Lesson> findAll(Pageable pageable);

    List<LessonReport> convertLessonReport(List<Lesson> lessons);

    List<LessonReport> findAllReport(Pageable pageable);

    void update(Lesson lesson);

    void deleteById(Long id);

    List<LessonWeekTableRow> getWeekTable(List<LessonReport> lessons);
}
