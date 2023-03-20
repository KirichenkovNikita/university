package ru.systematic.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.domain.Course;
import ru.systematic.university.service.CourseService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseDao dao;

    @Override
    public void add(Course course) {
        dao.save(course);
    }

    @Override
    public List<Course> findAll() {
        return (List<Course>) dao.findAll();
    }

    @Override
    public List<Course> findAll(Pageable pageable) {
        Page<Course> pages = dao.findAll(pageable);
        List<Course> result = new ArrayList<>();
        if (pages != null) {
            result.addAll(pages.getContent());
        }
        return result;
    }

    @Override
    public void update(Course course) {
        dao.save(course);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
