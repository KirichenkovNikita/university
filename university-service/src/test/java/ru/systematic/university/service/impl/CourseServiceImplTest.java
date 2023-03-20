package ru.systematic.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.domain.Course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    private final Course entity = new Course();

    @Mock
    private CourseDao dao;

    @InjectMocks
    private CourseServiceImpl service;

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
    void findAllShouldReturnAllEntitiesByPageable() {
        PageRequest request = PageRequest.of(0, 5);

        assertDoesNotThrow(() -> {
            service.findAll(request);
        });

        verify(dao).findAll(request);
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
