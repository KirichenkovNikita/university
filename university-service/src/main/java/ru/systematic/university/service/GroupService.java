package ru.systematic.university.service;

import org.springframework.data.domain.Pageable;
import ru.systematic.university.domain.Group;

import java.util.List;

public interface GroupService {
    void add(Group group);

    List<Group> findAll();

    List<Group> findAll(Pageable pageable);

    void update(Group group);

    void deleteById(Long id);
}
