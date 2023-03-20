package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Lesson;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonDao extends PagingAndSortingRepository<Lesson, Long> {
    List<Lesson> findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(
            Long courseId, LocalDateTime start, LocalDateTime end);
}
