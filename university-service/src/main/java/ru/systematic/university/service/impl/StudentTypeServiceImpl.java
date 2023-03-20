package ru.systematic.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.systematic.university.dao.StudentTypeDao;
import ru.systematic.university.domain.StudentType;
import ru.systematic.university.service.StudentTypeService;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentTypeServiceImpl implements StudentTypeService {
    private final StudentTypeDao dao;

    @Override
    public void add(StudentType type) {
        dao.save(type);
    }

    @Override
    public List<StudentType> findAll() {
        return (List<StudentType>) dao.findAll();
    }

    @Override
    public void update(StudentType type) {
        dao.save(type);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
