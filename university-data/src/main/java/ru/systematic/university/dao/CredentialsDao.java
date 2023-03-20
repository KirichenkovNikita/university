package ru.systematic.university.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.systematic.university.domain.Credentials;

import java.util.Optional;

@Repository
public interface CredentialsDao extends PagingAndSortingRepository<Credentials, Long> {
    Optional<Credentials> findByLogin(String login);
}
