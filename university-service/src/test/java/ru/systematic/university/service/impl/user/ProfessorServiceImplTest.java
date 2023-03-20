package ru.systematic.university.service.impl.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.dao.CredentialsDao;
import ru.systematic.university.dao.LessonDao;
import ru.systematic.university.dao.ProfessorDao;
import ru.systematic.university.domain.Course;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.domain.Professor;
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.domain.LessonReport;
import ru.systematic.university.service.exception.TimetableException;
import ru.systematic.university.service.user.ProfessorRegistrationRequest;
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

class ProfessorServiceImplTest extends AbstractUserServiceImplTest<Professor> {
    private List<Course> courses;
    private List<Lesson> lessons;

    @Mock
    private ProfessorDao userDao;

    @Mock
    private UserRegistrationValidator<Professor> validator;

    @Mock
    private LessonDao lessonDao;

    @Mock
    private CourseDao courseDao;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private CredentialsDao credentialsDao;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private ProfessorServiceImpl service;

    @Override
    protected void setEntity() {
        Professor professor1 = new Professor();
        professor1.setLinkedin("linkedin.com");
        professor1.setId((long) 1);
        professor1.setLogin("login");
        professor1.setPassword("password");
        professor1.setFirstName("Ivan");
        professor1.setLastName("Ivanov");
        professor1.setAvatar("/1");

        Professor professor2 = new Professor();
        professor2.setLinkedin("linkedin.com");
        professor2.setId((long) 2);
        professor2.setLogin("login");
        professor2.setPassword("password");
        professor2.setFirstName("Ivan");
        professor2.setLastName("Ivanov");
        professor2.setAvatar("/2");

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

        entities = Arrays.asList(professor1, professor2);
        userService = service;
        super.userDao = this.userDao;
        super.validator = this.validator;
        super.lessonDao = this.lessonDao;
        super.courseDao = this.courseDao;
        super.encoder = this.encoder;
        super.credentialsDao = this.credentialsDao;
    }

    @Test
    void registerShouldSaveUserWhenLoginCorrect() {
        ProfessorRegistrationRequest request = new ProfessorRegistrationRequest();
        request.setPassword("password");
        request.setLogin("login");
        when(credentialsDao.findByLogin("login")).thenReturn(Optional.of(new Credentials()));

        assertDoesNotThrow(() -> {
            ((ProfessorService)userService).register(request);
        });
    }

    @Test
    void getTimetableShouldReturnUsersLessonsWhenTimeCorrect() {
        Professor user = entities.get(0);

        when(courseDao.findByProfessorsId(user.getId())).thenReturn(courses);
        when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(0).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(0), lessons.get(1)));
        when(lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(courses.get(1).getId(), LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(Arrays.asList(lessons.get(2), lessons.get(3)));
        List<Lesson> expected = userService.getTimetable(user, LocalDateTime.MIN, LocalDateTime.MAX);
        assertThat(expected).isEqualTo(lessons);
    }

    @Test
    void getTimetableShouldReturnUsersLessonsWhenTimeIncorrect() {
        Professor user = entities.get(0);

        TimetableException exception = assertThrows(TimetableException.class, () -> {
            userService.getTimetable(user, LocalDateTime.MAX, LocalDateTime.MIN);
        });

        assertThat(exception.getMessage()).isEqualTo("The start date must be earlier than the end date");
        Mockito.verify(courseDao, Mockito.never()).findByProfessorsId(user.getId());
    }

    @Test
    void getTimetableReportShouldReturnUsersLessonsReport() {
        Professor user = entities.get(0);
        List<LessonReport> lessonsReport = new ArrayList<>();
        LessonReport lessonReport1 = new LessonReport();
        lessonReport1.setCourseName("Course 1");
        lessonReport1.setLocationName("Location 1");
        lessonsReport.add(lessonReport1);

        when(courseDao.findByProfessorsId(user.getId())).thenReturn(courses);
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
        List<Professor> expected = userService.getNotApprovedAvatarUser();

        verify(userDao).findAllByAvatarApprovedFalseAndAvatarNotNull();
        assertThat(expected).isEqualTo(entities);
    }
}
