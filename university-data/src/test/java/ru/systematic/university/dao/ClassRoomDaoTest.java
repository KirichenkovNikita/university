package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.ClassRoom;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomDaoTest extends AbstractDaoTest<ClassRoom> {

    @Autowired
    private ClassRoomDao dao;

    @Override
    void setData() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setNumber("Test");
        classRoom.setId(1L);
        classRoom.setName("Name");
        classRoom.setAddress("Test");
        classRoom.setLocationLink("Name");
        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setNumber("Test1");
        classRoom1.setId(2L);
        classRoom1.setName("Name1");
        classRoom1.setAddress("Test1");
        classRoom1.setLocationLink("Name1");
        List<ClassRoom> classRooms = new ArrayList<>();
        classRooms.add(classRoom);
        classRooms.add(classRoom1);

        super.entities = classRooms;
        super.dao = this.dao;
    }
}
