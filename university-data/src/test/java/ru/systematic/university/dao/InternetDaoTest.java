package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Internet;

import java.util.Arrays;

public class InternetDaoTest extends AbstractDaoTest<Internet>{

    @Autowired
    private InternetDao dao;

    @Override
    void setData() {
        Internet internet1 = new Internet();
        internet1.setId(1L);
        internet1.setAddress("https://zoom.us/1");
        internet1.setName("Zoom лекции по химии");

        Internet internet2 = new Internet();
        internet2.setId(2L);
        internet2.setAddress("https://zoom.us/2");
        internet2.setName("Zoom лекции по физике");

        super.entities = Arrays.asList(internet1, internet2);
        super.dao = this.dao;
    }
}
