package ru.systematic.university.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import ru.systematic.university.service.exception.InvalidLoginException;
import ru.systematic.university.service.exception.TimetableException;
import ru.systematic.university.service.user.StudentRegistrationRequest;
import ru.systematic.university.service.validator.EntityValidator;
import ru.systematic.university.service.validator.user.AvatarFileValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl extends AbstractUserServiceImpl<Student> implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final EntityValidator<StudentRegistrationRequest> validator;
    private final LessonService lessonService;
    private final StudentDao studentDao;

    public StudentServiceImpl(@Value("${upload.path}") String uploadPath,
                              StudentDao userDao,
                              EntityValidator<StudentRegistrationRequest> validator,
                              LessonDao lessonDao,
                              CourseDao courseDao,
                              PasswordEncoder encoder,
                              CredentialsDao credentialsDao,
                              LessonService lessonService,
                              AvatarFileValidator fileValidator) {
        super(uploadPath, userDao, credentialsDao, lessonDao, courseDao, encoder, fileValidator);
        this.validator = validator;
        this.lessonService = lessonService;
        this.studentDao = userDao;
    }

    @Override
    public Student register(StudentRegistrationRequest user) {
        validator.validate(user);
        Student entity = user.getEntity();
        Optional<Credentials> registerUser = credentialsDao.findByLogin(entity.getLogin());
        if (registerUser.isPresent()) {
            LOGGER.error("Login already exists: login - " + user.getLogin());
            throw new InvalidLoginException("Login already exists");
        }

        String password = encoder.encode(user.getPassword());
        Credentials credentials = new Credentials();
        credentials.setLogin(entity.getLogin());
        credentials.setPassword(password);

        credentialsDao.save(credentials);
        credentials = credentialsDao.findByLogin(credentials.getLogin()).get();
        entity.setPassword(password);
        entity.setCredentialsId(credentials.getId());
        userDao.save(entity);
        return entity;
    }

    @Override
    public List<Lesson> getTimetable(Student user, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            LOGGER.error(String.format(LOGGER_FORMAT, start, end));
            throw new TimetableException("The start date must be earlier than the end date");
        }

        List<Course> courses = courseDao.findByStudentsId(user.getId());
        return courses.stream()
                .map(course ->
                        lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(course.getId(), start, end))
                .collect(ArrayList::new, Collection::addAll, Collection::addAll);
    }

    @Override
    public List<LessonReport> getTimetableReport(Student user, LocalDateTime start, LocalDateTime end) {
        List<Lesson> lessons = getTimetable(user, start, end);
        return lessonService.convertLessonReport(lessons);
    }

    @Override
    public void addToCourse(Course course, Student user) {
        Optional<Student> optional = studentDao.findById(user.getId());
        if (optional.isPresent()) {
            Student student = optional.get();
            student.getCourses().add(course);
            studentDao.save(student);
        }
    }

    @Override
    public void deleteFromCourse(Course course, Student user) {
        Optional<Student> optional = studentDao.findById(user.getId());
        if (optional.isPresent()) {
            Student student = optional.get();
            student.getCourses().remove(course);
            studentDao.save(student);
        }
    }

    @Override
    public List<Student> getNotApprovedAvatarUser() {
        return studentDao.findAllByAvatarApprovedFalseAndAvatarNotNull();
    }

    @Override
    public void approveAvatar(Long id) {
        setApprove(true, id);
    }

    @Override
    public void banAvatar(Long id) {
        setApprove(false, id);
    }

    @Override
    public List<Student> findAllByCoursesName(String courseName) {
        return studentDao.findAllByCoursesName(courseName);
    }

    private void setApprove(boolean value, Long id) {
        Optional<Student> optional = studentDao.findById(id);
        if (optional.isPresent()) {
            Student student = optional.get();
            student.setAvatarApproved(value);
            studentDao.save(student);
        }
    }
}
