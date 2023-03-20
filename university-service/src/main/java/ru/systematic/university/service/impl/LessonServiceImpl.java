package ru.systematic.university.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.domain.LessonReport;
import ru.systematic.university.service.domain.week.LessonWeekTableCell;
import ru.systematic.university.service.domain.week.LessonWeekTableRow;
import ru.systematic.university.service.domain.week.LessonWeekTableTimes;
import ru.systematic.university.service.exception.EntityNotFoundException;
import ru.systematic.university.service.validator.EntityValidator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);
    private static final int DAY_COUNT = 7;
    private final LessonDao lessonDao;
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final ClassRoomDao classRoomDao;
    private final ProfessorDao professorDao;
    private final EntityValidator<Lesson> validator;

    @Override
    public void add(Lesson entity) {
        validator.validate(entity);
        Optional<Course> course = courseDao.findById(entity.getCourseId());

        if (!course.isPresent()) {
            LOGGER.error("Course must be recorded");
            throw new EntityNotFoundException("Course must be recorded", 400);
        }

        List<Student> students = studentDao.findAllByCoursesName(course.get().getName());
        if (students.isEmpty()) {
            LOGGER.error("No student enrolled in the lesson: courseId - " + entity.getCourseId());
            throw new EntityNotFoundException("No student enrolled in the lesson", 400);
        }

        List<Professor> professors = professorDao.findAllByCoursesName(course.get().getName());
        if (professors.isEmpty()) {
            LOGGER.error("No professors enrolled in the lesson");
            throw new EntityNotFoundException("No professors enrolled in the lesson", 400);
        }

        lessonDao.save(entity);
    }

    @Override
    public List<Lesson> findAll() {
        return (List<Lesson>) lessonDao.findAll();
    }

    @Override
    public List<Lesson> findAll(Pageable pageable) {
        Page<Lesson> pages = lessonDao.findAll(pageable);
        List<Lesson> result = new ArrayList<>();
        if (pages != null) {
            result.addAll(pages.getContent());
        }
        return result;
    }

    @Override
    public List<LessonReport> convertLessonReport(List<Lesson> lessons) {
        List<LessonReport> result = new ArrayList<>();
        lessons.forEach(lesson -> {
            LessonReport report = new LessonReport();
            ClassRoom room = classRoomDao.findById(lesson.getLocationId()).get();
            report.setCourseName(courseDao.findById(lesson.getCourseId()).get().getName());
            report.setLocationName(room.getName());
            report.setLocationHref(room.getLocationLink());
            report.setStart(lesson.getStartTime());
            report.setEnd(lesson.getEndTime());
            report.setId(lesson.getId());

            result.add(report);
        });

        return result.stream()
                .sorted(Comparator.comparing(LessonReport::getStart))
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonReport> findAllReport(Pageable pageable) {
        List<Lesson> lessons = lessonDao.findAll(pageable).getContent();
        return convertLessonReport(lessons);
    }

    @Override
    public void update(Lesson lesson) {
        lessonDao.save(lesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonDao.deleteById(id);
    }

    @Override
    public List<LessonWeekTableRow> getWeekTable(List<LessonReport> lessons) {
        List<LessonWeekTableRow> result = new ArrayList<>();

        for (LocalTime time : LessonWeekTableTimes.TIMES) {
            List<LessonReport> currentLessons = new ArrayList<>();

            lessons.forEach(lesson -> {
                if (lesson.getStart().toLocalTime().equals(time)) {
                    currentLessons.add(lesson);
                }
            });

            result.add(createRow(currentLessons, time));
        }

        return result;
    }

    private LessonWeekTableRow createRow(List<LessonReport> lessons, LocalTime time) {
        LessonWeekTableRow row = new LessonWeekTableRow();
        LessonWeekTableCell timeCell = new LessonWeekTableCell(time.toString(), "");
        row.add(timeCell);

        for (int i = 1; i < DAY_COUNT; i++) {
            int finalI = i;
            AtomicInteger countLessonInCell = new AtomicInteger();
            lessons.forEach(lesson -> {
                if (finalI == lesson.getStart().getDayOfWeek().getValue()) {
                    LessonWeekTableCell lessonCell = new LessonWeekTableCell(
                            lesson.getCourseName(),
                            lesson.getLocationHref());
                    row.add(lessonCell);
                    countLessonInCell.getAndIncrement();
                }
            });

            if (countLessonInCell.get() == 0) {
                row.add(LessonWeekTableCell.getEmptyCell());
            }
        }

        return row;
    }
}
