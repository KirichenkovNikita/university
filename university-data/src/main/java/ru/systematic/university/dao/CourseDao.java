package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Course;

import java.util.List;

@Repository
public interface CourseDao extends PagingAndSortingRepository<Course, Long> {
    List<Course> findByStudentsId(Long studentId);

    List<Course> findByProfessorsId(Long professorId);
}
