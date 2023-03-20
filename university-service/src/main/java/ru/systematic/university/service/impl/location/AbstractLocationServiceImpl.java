package ru.systematic.university.service.impl.location;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.systematic.university.domain.Location;
import ru.systematic.university.service.LocationService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractLocationServiceImpl<E extends Location> implements LocationService<E> {
    private final PagingAndSortingRepository<E, Long> dao;

    @Override
    public void add(E entity) {
        dao.save(entity);
    }

    @Override
    public List<E> findAll() {
        return (List<E>) dao.findAll();
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        Page<E> pages = dao.findAll(pageable);
        List<E> result = new ArrayList<>();
        if (pages != null) {
            result.addAll(pages.getContent());
        }
        return result;
    }

    @Override
    public void update(E entity) {
        dao.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
