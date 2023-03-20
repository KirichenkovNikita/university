package ru.systematic.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.systematic.university.domain.Credentials;

import java.util.Arrays;

public class CredentialsDaoTest extends AbstractDaoTest<Credentials>{

    @Autowired
    private CredentialsDao dao;

    @Override
    void setData() {
        Credentials entity1 = new Credentials();
        entity1.setId(1L);
        entity1.setLogin("1");
        entity1.setPassword("Улица 2");

        Credentials entity2 = new Credentials();
        entity2.setId(2L);
        entity2.setLogin("2");
        entity2.setPassword("Улица 2");

        super.entities = Arrays.asList(entity1, entity2);
        super.dao = this.dao;
    }
}
