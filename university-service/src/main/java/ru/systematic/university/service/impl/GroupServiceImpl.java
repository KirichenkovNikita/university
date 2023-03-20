package ru.systematic.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.systematic.university.dao.GroupDao;
import ru.systematic.university.domain.Group;
import ru.systematic.university.service.GroupService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupDao dao;

    @Override
    public void add(Group group) {
        dao.save(group);
    }

    @Override
    public List<Group> findAll() {
        return (List<Group>) dao.findAll();
    }

    @Override
    public List<Group> findAll(Pageable pageable) {
        Page<Group> pages = dao.findAll(pageable);
        List<Group> result = new ArrayList<>();
        if (pages != null) {
            result.addAll(pages.getContent());
        }
        return result;
    }

    @Override
    public void update(Group group) {
        dao.save(group);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
