package ru.systematic.university.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public abstract class AbstractDaoTest<E> {
    protected PagingAndSortingRepository<E, Long> dao;
    protected List<E> entities;

    @Before
    public void setConnection() {
        setData();
    }

    @Test
    public void saveShouldSaveEntityNotNull() {
        dao.save(entities.get(0));
        List<E> actual = (List<E>) dao.findAll();
        List<E> expected = Collections.singletonList(entities.get(0));
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void saveAllShouldCreateNothingWhenEntitiesIsEmpty()  {
        List<E> expected = new ArrayList<>();
        dao.saveAll(expected);
        List<E> actual = (List<E>) dao.findAll();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void saveAllShouldAddDataWhenEntitiesNotEmpty()   {
        dao.saveAll(entities);
        List<E> actual = (List<E>) dao.findAll();
        assertThat(entities).isEqualTo(actual);
    }

    @Test
    public void findByIdShouldReturnEntityWhenEntityIsExist() {
        dao.saveAll(entities);
        Optional<E> actual = dao.findById((long) 1);
        E expected = entities.get(0);
        assertThat(actual.isPresent()).isTrue();
        assertThat(expected).isEqualTo(actual.get());
    }

    @Test
    public void findByIdShouldReturnOptionalWhenEntityNotExist() {
        dao.saveAll(entities);
        Optional<E> actual = dao.findById((long) 1500);
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    public void findAllShouldReturnNothingWhenEntitiesIsEmpty()   {
        List<E> expected = new ArrayList<>();
        List<E> actual = (List<E>) dao.findAll();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void findAllShouldReturnDataWhenEntitiesNotEmpty()   {
        dao.saveAll(entities);
        List<E> actual = (List<E>) dao.findAll();
        assertThat(entities).isEqualTo(actual);
    }

    @Test
    public void deleteByIdShouldNotRemoveAnyStudentWhenIdIsExist()   {
        dao.saveAll(entities);
        dao.deleteById((long) 1);
        List<E> actual = (List<E>) dao.findAll();
        List<E> expected = Collections.singletonList(entities.get(1));
        assertThat(expected).isEqualTo(actual);
    }

    abstract void setData();
}
