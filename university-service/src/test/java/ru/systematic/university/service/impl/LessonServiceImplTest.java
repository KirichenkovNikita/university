package ru.systematic.university.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.systematic.university.dao.ClassRoomDao;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.dao.LessonDao;
import ru.systematic.university.dao.ProfessorDao;
import ru.systematic.university.dao.StudentDao;
import ru.systematic.university.domain.ClassRoom;
import ru.systematic.university.domain.Course;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.domain.Professor;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.domain.LessonReport;
import ru.systematic.university.service.exception.EntityNotFoundException;
import ru.systematic.university.service.validator.EntityValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    private final Lesson entity = new Lesson();

    @Mock
    LessonDao dao;

    @Mock
    private CourseDao courseDao;

    @Mock
    private StudentDao studentDao;

    @Mock
    private ProfessorDao professorDao;

    @Mock
    private EntityValidator<Lesson> validator;

    @Mock
    private ClassRoomDao classRoomDao;

    @InjectMocks
    LessonServiceImpl service;

    @Test
    void addShouldAddEntityToDBWhenEntityCorrect() {
        List<Student> students = new ArrayList<>();
        List<Professor> professors = new ArrayList<>();
        Course course = new Course();

        students.add(new Student());
        professors.add(new Professor());
        course.setName("Тестовый курс");
        course.setId((long) 1);
        entity.setCourseId((long) 1);

        when(courseDao.findById((long) 1)).thenReturn(Optional.of(course));
        when(studentDao.findAllByCoursesName(course.getName())).thenReturn(students);
        when(professorDao.findAllByCoursesName(course.getName())).thenReturn(professors);

        assertDoesNotThrow(() -> {
            service.add(entity);
        });

        verify(dao).save(entity);
        verify(courseDao).findById((long) 1);
        verify(studentDao).findAllByCoursesName(course.getName());
        verify(professorDao).findAllByCoursesName(course.getName());
    }

    @Test
    void addShouldThrowExceptionWhenCourseNotFound() {
        Course course = new Course();
        course.setId((long) 1);
        entity.setCourseId((long) 1);
        when(courseDao.findById((long) 1)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.add(entity);
        });
        assertThat(exception.getMessage()).isEqualTo("Course must be recorded");

        verify(dao, never()).save(entity);
        verify(courseDao).findById((long) 1);
        verify(studentDao, never()).findAllByCoursesName(course.getName());
        verify(professorDao, never()).findAllByCoursesName(course.getName());
    }

    @Test
    void addShouldThrowExceptionWhenStudentsNotFound() {
        List<Student> students = new ArrayList<>();
        Course course = new Course();
        course.setName("Тестовый курс");
        course.setId((long) 1);
        entity.setCourseId((long) 1);
        when(courseDao.findById((long) 1)).thenReturn(Optional.of(course));
        when(studentDao.findAllByCoursesName(course.getName())).thenReturn(students);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.add(entity);
        });
        assertThat(exception.getMessage()).isEqualTo("No student enrolled in the lesson");

        verify(dao, never()).save(entity);
        verify(courseDao).findById((long) 1);
        verify(studentDao).findAllByCoursesName(course.getName());
        verify(professorDao, never()).findAllByCoursesName(course.getName());
    }

    @Test
    void addShouldThrowExceptionWhenProfessorsNotFound() {
        List<Student> students = new ArrayList<>();
        List<Professor> professors = new ArrayList<>();
        Course course = new Course();
        course.setId((long) 1);
        students.add(new Student());
        course.setName("Тестовый курс");
        entity.setCourseId((long) 1);

        when(courseDao.findById((long) 1)).thenReturn(Optional.of(course));
        when(studentDao.findAllByCoursesName(course.getName())).thenReturn(students);
        when(professorDao.findAllByCoursesName(course.getName())).thenReturn(professors);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.add(entity);
        });
        assertThat(exception.getMessage()).isEqualTo("No professors enrolled in the lesson");

        verify(dao, never()).save(entity);
        verify(courseDao).findById((long) 1);
        verify(studentDao).findAllByCoursesName(course.getName());
        verify(professorDao).findAllByCoursesName(course.getName());
    }

    @Test
    void findAllShouldReturnAllEntities() {
        assertDoesNotThrow(() -> {
            service.findAll();
        });

        verify(dao).findAll();
    }

    @Test
    void findAllShouldReturnAllEntitiesByPageable() {
        PageRequest request = PageRequest.of(0, 5);

        assertDoesNotThrow(() -> {
            service.findAll(request);
        });

        verify(dao).findAll(request);
    }

    @Test
    void updateShouldUpdateEntity() {
        entity.setId((long) 1);

        assertDoesNotThrow(() -> {
            service.update(entity);
        });

        verify(dao).save(entity);
    }

    @Test
    void deleteByIdShouldDeleteEntityById() {
        assertDoesNotThrow(() -> {
            service.deleteById((long) 1);
        });

        verify(dao).deleteById((long) 1);
    }

    @Test
    void convertLessonReportShouldConvertLessonArray() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setCourseId((long) 1);
        lesson1.setLocationId((long) 1);
        lesson1.setStartTime(LocalDateTime.MIN);
        Lesson lesson2 = new Lesson();
        lesson2.setCourseId((long) 2);
        lesson2.setLocationId((long) 2);
        lesson2.setStartTime(LocalDateTime.MAX);
        lessons.add(lesson1);
        lessons.add(lesson2);

        List<LessonReport> lessonsReport = new ArrayList<>();
        LessonReport lessonReport1 = new LessonReport();
        lessonReport1.setCourseName("Course 1");
        lessonReport1.setLocationName("Location 1");
        lessonReport1.setStart(LocalDateTime.MIN);
        LessonReport lessonReport2 = new LessonReport();
        lessonReport2.setCourseName("Course 2");
        lessonReport2.setLocationName("Location 2");
        lessonReport2.setStart(LocalDateTime.MAX);
        lessonsReport.add(lessonReport1);
        lessonsReport.add(lessonReport2);

        Course course1 = new Course();
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setName("Course 2");

        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setName("Location 1");

        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setName("Location 2");

        when(courseDao.findById((long) 1)).thenReturn(Optional.of(course1));
        when(courseDao.findById((long) 2)).thenReturn(Optional.of(course2));
        when(classRoomDao.findById((long) 1)).thenReturn(Optional.of(classRoom1));
        when(classRoomDao.findById((long) 2)).thenReturn(Optional.of(classRoom2));

        List<LessonReport> expected = service.convertLessonReport(lessons);

        assertThat(expected).isEqualTo(lessonsReport);
    }

    @Test
    void findAllReportShouldGetLessonsAndConvertLessonArray() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson1 = new Lesson();
        lesson1.setCourseId((long) 1);
        lesson1.setLocationId((long) 1);
        lesson1.setStartTime(LocalDateTime.MIN);
        Lesson lesson2 = new Lesson();
        lesson2.setCourseId((long) 2);
        lesson2.setLocationId((long) 2);
        lesson2.setStartTime(LocalDateTime.MAX);
        lessons.add(lesson1);
        lessons.add(lesson2);
        Page<Lesson> page = new PageImpl<>(lessons);

        List<LessonReport> lessonsReport = new ArrayList<>();
        LessonReport lessonReport1 = new LessonReport();
        lessonReport1.setCourseName("Course 1");
        lessonReport1.setLocationName("Location 1");
        lessonReport1.setStart(LocalDateTime.MIN);
        LessonReport lessonReport2 = new LessonReport();
        lessonReport2.setCourseName("Course 2");
        lessonReport2.setLocationName("Location 2");
        lessonReport2.setStart(LocalDateTime.MAX);
        lessonsReport.add(lessonReport1);
        lessonsReport.add(lessonReport2);

        Course course1 = new Course();
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setName("Course 2");

        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setName("Location 1");

        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setName("Location 2");

        PageRequest pageRequest = PageRequest.of(0, 10);
        when(dao.findAll(pageRequest)).thenReturn(page);
        when(courseDao.findById((long) 1)).thenReturn(Optional.of(course1));
        when(courseDao.findById((long) 2)).thenReturn(Optional.of(course2));
        when(classRoomDao.findById((long) 1)).thenReturn(Optional.of(classRoom1));
        when(classRoomDao.findById((long) 2)).thenReturn(Optional.of(classRoom2));

        List<LessonReport> expected = service.findAllReport(pageRequest);

        assertThat(expected).isEqualTo(lessonsReport);
    }
}
