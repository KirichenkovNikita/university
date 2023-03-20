package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.StudentType;

@Repository
public interface StudentTypeDao extends PagingAndSortingRepository<StudentType, Long> {
}
