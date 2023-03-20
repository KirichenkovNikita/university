package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Group;

@Repository
public interface GroupDao extends PagingAndSortingRepository<Group, Long> {
}
