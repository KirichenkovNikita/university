package ru.systematic.university.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import ru.systematic.university.service.validator.EntityValidator;
import ru.systematic.university.service.validator.user.AvatarFileValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl extends AbstractUserServiceImpl<Professor> implements ProfessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorServiceImpl.class);
    private final EntityValidator<ProfessorRegistrationRequest> validator;
    private final LessonService lessonService;
    private final ProfessorDao professorDao;

    public ProfessorServiceImpl(@Value("${upload.path}") String uploadPath,
                                ProfessorDao userDao,
                                EntityValidator<ProfessorRegistrationRequest> validator,
                                LessonDao lessonDao,
                                CourseDao courseDao,
                                PasswordEncoder encoder,
                                CredentialsDao credentialsDao,
                                LessonService lessonService,
                                AvatarFileValidator fileValidator) {
        super(uploadPath, userDao, credentialsDao, lessonDao, courseDao, encoder, fileValidator);
        this.validator = validator;
        this.lessonService = lessonService;
        this.professorDao = userDao;
    }

    @Override
    public Professor register(ProfessorRegistrationRequest user) {
        validator.validate(user);
        Professor entity = user.getEntity();

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
    public List<Lesson> getTimetable(Professor user, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            LOGGER.error(String.format(LOGGER_FORMAT, start, end));
            throw new TimetableException("The start date must be earlier than the end date");
        }

        List<Course> courses = courseDao.findByProfessorsId(user.getId());
        return courses.stream()
                .map(course ->
                        lessonDao.findLessonsByCourseIdAndStartTimeAfterAndEndTimeBefore(course.getId(), start, end))
                .collect(ArrayList::new, Collection::addAll, Collection::addAll);
    }

    @Override
    public List<LessonReport> getTimetableReport(Professor user, LocalDateTime start, LocalDateTime end) {
        List<Lesson> lessons = getTimetable(user, start, end);
        return lessonService.convertLessonReport(lessons);
    }

    @Override
    public void addToCourse(Course course, Professor user) {
        Optional<Professor> optional = professorDao.findById(user.getId());
        if (optional.isPresent()) {
            Professor professor = optional.get();
            professor.getCourses().add(course);
            professorDao.save(professor);
        }
    }

    @Override
    public void deleteFromCourse(Course course, Professor user) {
        Optional<Professor> optional = professorDao.findById(user.getId());
        if (optional.isPresent()) {
            Professor professor = optional.get();
            professor.getCourses().remove(course);
            professorDao.save(professor);
        }
    }

    @Override
    public List<Professor> getNotApprovedAvatarUser() {
        return professorDao.findAllByAvatarApprovedFalseAndAvatarNotNull();
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
    public List<Professor> findAllByCoursesName(String courseName) {
        return professorDao.findAllByCoursesName(courseName);
    }

    private void setApprove(boolean value, Long id) {
        Optional<Professor> optional = professorDao.findById(id);
        if (optional.isPresent()) {
            Professor professor = optional.get();
            professor.setAvatarApproved(value);
            professorDao.save(professor);
        }
    }
}
