package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Internet;

@Repository
public interface InternetDao  extends PagingAndSortingRepository<Internet, Long> {
}
