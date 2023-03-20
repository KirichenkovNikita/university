package ru.systematic.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.systematic.university.dao.StudentTypeDao;
import ru.systematic.university.domain.StudentType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentTypeServiceImplTest {
    private final StudentType entity = new StudentType();

    @Mock
    private StudentTypeDao dao;

    @InjectMocks
    private StudentTypeServiceImpl service;

    @Test
    void addShouldAddEntityToDB() {
        assertDoesNotThrow(() -> {
            service.add(entity);
        });

        verify(dao).save(entity);
    }

    @Test
    void findAllShouldReturnAllEntities() {
        assertDoesNotThrow(() -> {
            service.findAll();
        });

        verify(dao).findAll();
    }

    @Test
    void updateShouldUpdateEntity() {
        entity.setId((long) 1);

        assertDoesNotThrow(() -> {
            service.update(entity);
        });

        verify(dao).save(entity);
    }

    @Test
    void deleteByIdShouldDeleteEntityById() {
        assertDoesNotThrow(() -> {
            service.deleteById((long) 1);
        });

        verify(dao).deleteById((long) 1);
    }
}
