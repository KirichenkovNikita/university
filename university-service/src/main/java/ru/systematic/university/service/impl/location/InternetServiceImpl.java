package ru.systematic.university.service.impl.location;

import org.springframework.stereotype.Service;
import ru.systematic.university.dao.InternetDao;
import ru.systematic.university.domain.Internet;
import ru.systematic.university.service.InternetService;

@Service
public class InternetServiceImpl extends AbstractLocationServiceImpl<Internet> implements InternetService {
    public InternetServiceImpl(InternetDao dao) {
        super(dao);
    }
}
