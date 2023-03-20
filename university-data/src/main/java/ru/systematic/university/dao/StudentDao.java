package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Student;

import java.util.List;

@Repository
public interface StudentDao extends PagingAndSortingRepository<Student, Long> {
    List<Student> findAllByAvatarApprovedFalseAndAvatarNotNull();

    List<Student> findAllByCoursesName(String name);
}
