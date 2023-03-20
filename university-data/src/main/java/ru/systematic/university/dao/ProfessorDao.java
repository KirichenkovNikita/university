package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Professor;

import java.util.List;

@Repository
public interface ProfessorDao extends PagingAndSortingRepository<Professor, Long> {
    List<Professor> findAllByAvatarApprovedFalseAndAvatarNotNull();

    List<Professor> findAllByCoursesName(String name);
}
