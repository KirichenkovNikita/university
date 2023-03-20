package ru.systematic.university.service;

import org.springframework.data.domain.Pageable;
import ru.systematic.university.domain.Location;

import java.util.List;

public interface LocationService<E extends Location> {
    void add(E entity);

    List<E> findAll();

    List<E> findAll(Pageable pageable);

    void update(E entity);

    void deleteById(Long id);

}
