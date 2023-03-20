package ru.systematic.university.service.impl.location;

import org.springframework.stereotype.Service;
import ru.systematic.university.dao.ClassRoomDao;
import ru.systematic.university.domain.ClassRoom;
import ru.systematic.university.service.ClassRoomService;

@Service
public class ClassRoomServiceImpl extends AbstractLocationServiceImpl<ClassRoom> implements ClassRoomService {
    public ClassRoomServiceImpl(ClassRoomDao dao) {
        super(dao);
    }
}
