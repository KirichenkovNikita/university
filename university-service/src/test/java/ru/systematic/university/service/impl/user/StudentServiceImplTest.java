package ru.systematic.university.service.impl.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.dao.CredentialsDao;
import ru.systematic.university.dao.LessonDao;
import ru.systematic.university.dao.StudentDao;
import ru.systematic.university.domain.Course;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.domain.LessonReport;
import ru.systematic.university.service.exception.TimetableException;
import ru.systematic.university.service.user.StudentRegistrationRequest;
import ru.systematic.university.service.validator.user.UserRegistrationValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

;

class StudentServiceImplTest extends AbstractUserServiceImplTest<Student> {
    private List<Course> courses;
    private List<Lesson> lessons;

    @Mock
    private StudentDao userDao;

    @Mock
    private UserRegistrationValidator<Student> validator;

    @Mock
    private LessonDao lessonDao;

    @Mock
    private CourseDao courseDao;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private LessonService lessonService;

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private StudentServiceImpl service;

    @Override
    protected void setEntity() {
        Student student1 = new Student();
        student1.setId((long) 1);
        student1.setLogin("login");
        student1.setPassword("password");
        student1.setFirstName("Ivan");
        student1.setLastName("Ivanov");
        student1.setAvatar("/1.png");

        Student student2 = new Student();
        student2.setId((long) 2);
        student2.setLogin("login");
        student2.setPassword("password");
        student2.setFirstName("Ivan");
        student2.setLastName("Ivanov");
        student2.setAvatar("/2.png");

        entities = Arrays.asList(student1, student2);

        Course course1 = new Course();
        course1.setId((long) 1);
        course1.setName("Химия");
        course1.setDescription("Органическая химия");

        Course course2 = new Course();
        course2.setId((long) 2);
        course2.setName("Физика");
        course2.setDescription("Оптика");

        courses = Arrays.asList(course1, course2);

        Lesson lesson1 = new Lesson();
        lesson1.setCourseId((long) 1);
        lesson1.setId((long) 1);
        lesson1.setEndTime(LocalDateTime.now());
        lesson1.setStartTime(LocalDateTime.now());

        Lesson lesson2 = new Lesson();
        lesson1.setCourseId((long) 1);
        lesson2.setId((long) 2);
        lesson2.setEndTime(LocalDateTime.now());
        lesson2.setStartTime(LocalDateTime.now());

        Lesson lesson3 = new Lesson();
        lesson3.setCourseId((long) 2);
        lesson3.setId((long) 3);
        lesson3.setEndTime(LocalDateTime.now());
        lesson3.setStartTime(LocalDateTime.now());

        Lesson lesson4 = new Lesson();
        lesson4.setCourseId((long) 2);
        lesson4.setId((long) 4);
        lesson4.setEndTime(LocalDateTime.now());
        lesson4.setStartTime(LocalDateTime.now());

        lessons = Arrays.asList(lesson1, lesson2, lesson3, lesson4);

        userService = service;
        super.userDao = this.userDao;
        super.validator = this.validator;
        super.lessonDao = this.lessonDao;
        super.courseDao = this.courseDao;
        super.encoder = this.encoder;
        super.credentialsDao = this.credentialsDao;
    }

    @Test
    void registerShouldThrowExceptionWhenLoginIncorrect() {
        StudentRegistrationRequest request = new StudentRegistrationRequest();
        request.setPassword("password");
        request.setLogin("login");
        when(credentialsDao.findByLogin("login")).thenReturn(Optional.of(new Credentials()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ((StudentService)userService).register(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Login already exists");
    }

    @Test
    void registerShouldSaveUserWhenLoginCorrect() {
        StudentRegistrationRequest request = new StudentRegistrationRequest();
        request.setPassword("password");
        request.setLogin("login");
        when(credentialsDao.findByLogin("login")).thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Credentials()));

        assertDoesNotThrow(() -> {
            ((StudentService)userService).register(request);
        });
    }


    @Test
    void getTimetableShouldReturnUsersLessonsWhenTimeCorrect() {
        Student user = entities.get(0);

        Mockito.when(courseDao.findByStudentsId(user.getId())).thenReturn(courses);
        Mockito.when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(0).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(0), lessons.get(1)));
        Mockito.when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(1).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(2), lessons.get(3)));
        List<Lesson> expected = userService.getTimetable(user, LocalDateTime.MIN, LocalDateTime.MAX);
        assertThat(expected).isEqualTo(lessons);
    }

    @Test
    void getTimetableShouldReturnUsersLessonsWhenTimeIncorrect() {
        Student user = entities.get(0);

        TimetableException exception = assertThrows(TimetableException.class, () -> {
            userService.getTimetable(user, LocalDateTime.MAX, LocalDateTime.MIN);
        });

        assertThat(exception.getMessage()).isEqualTo("The start date must be earlier than the end date");
        Mockito.verify(courseDao, Mockito.never()).findByStudentsId(user.getId());
    }

    @Test
    void getTimetableReportShouldReturnUsersLessonsReport() {
        Student user = entities.get(0);
        List<LessonReport> lessonsReport = new ArrayList<>();
        LessonReport lessonReport1 = new LessonReport();
        lessonReport1.setCourseName("Course 1");
        lessonReport1.setLocationName("Location 1");
        lessonsReport.add(lessonReport1);

        when(courseDao.findByStudentsId(user.getId())).thenReturn(courses);
        when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(0).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(0), lessons.get(1)));
        when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(1).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(2), lessons.get(3)));
        List<Lesson> result = userService.getTimetable(user, LocalDateTime.MIN, LocalDateTime.MAX);
        when(lessonService.convertLessonReport(result)).thenReturn(lessonsReport);
        List<LessonReport> expected = userService.getTimetableReport(user, LocalDateTime.MIN, LocalDateTime.MAX);
        assertThat(expected).isEqualTo(lessonsReport);
    }

    @Test
    void getNotApprovedAvatarUserShouldReturnUsers() {
        when(userDao.findAllByAvatarApprovedFalseAndAvatarNotNull()).thenReturn(entities);
        List<Student> expected = userService.getNotApprovedAvatarUser();

        verify(userDao).findAllByAvatarApprovedFalseAndAvatarNotNull();
        assertThat(expected).isEqualTo(entities);
    }
}
