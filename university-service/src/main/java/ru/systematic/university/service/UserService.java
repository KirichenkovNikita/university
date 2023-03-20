package ru.systematic.university.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.systematic.university.domain.Course;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.domain.LessonReport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService<E extends User> {
    Credentials login(String login, String password);

    List<Lesson> getTimetable(E user, LocalDateTime start, LocalDateTime end);

    List<LessonReport> getTimetableReport(E user, LocalDateTime start, LocalDateTime end);

    List<E> findAll();

    List<E> findAll(Pageable pageable);

    Optional<E> findById(Long id);

    void update(E entity);

    void update(E entity, MultipartFile file) throws IOException;

    void deleteById(Long id);

    void addToCourse(Course course, E user);

    void deleteFromCourse(Course course, E user);

    List<E> getNotApprovedAvatarUser();

    void approveAvatar(Long id);

    void banAvatar(Long id);

    List<E> findAllByCoursesName(String courseName);
}
