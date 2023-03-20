package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Group;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupDaoTest extends AbstractDaoTest<Group>{

    @Autowired
    private GroupDao dao;

    @Override
    void setData() {
        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("1");
        group1.setStudentsCount(new ArrayList<>());

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("2");
        group2.setStudentsCount(new ArrayList<>());

        super.entities = Arrays.asList(group1, group2);
        super.dao = this.dao;
    }
}
