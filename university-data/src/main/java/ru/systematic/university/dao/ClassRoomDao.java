package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.ClassRoom;

@Repository
public interface ClassRoomDao extends PagingAndSortingRepository<ClassRoom, Long> {
}
