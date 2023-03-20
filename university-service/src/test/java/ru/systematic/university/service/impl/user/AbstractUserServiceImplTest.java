package ru.systematic.university.service.impl.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.dao.CredentialsDao;
import ru.systematic.university.dao.LessonDao;
import ru.systematic.university.domain.Course;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.UserService;
import ru.systematic.university.service.exception.EntityNotFoundException;
import ru.systematic.university.service.validator.EntityValidator;
import ru.systematic.university.service.user.UserRegistrationRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractUserServiceImplTest<E extends User> {
    protected List<E> entities;
    protected UserService<E> userService;
    protected PagingAndSortingRepository<E, Long> userDao;
    protected EntityValidator<UserRegistrationRequest<E>> validator;
    protected LessonDao lessonDao;
    protected CourseDao courseDao;
    protected PasswordEncoder encoder;
    protected CredentialsDao credentialsDao;

    @BeforeEach
    void setData() {
        setEntity();
    }

    @Test
    void loginShouldReturnUserWhenUserRegistered() {
        Credentials credentials = new Credentials();
        credentials.setPassword("login");
        credentials.setLogin("password");
        when(credentialsDao.findByLogin("login")).thenReturn(Optional.ofNullable(credentials));
        when(encoder.matches("unencrypted password", credentials.getPassword())).thenReturn(true);

        Credentials expected = userService.login("login", "unencrypted password");
        assertThat(expected).isEqualTo(credentials);
    }

    @Test
    void loginShouldThrowExceptionWhenUserNotRegistered() {
        E user = entities.get(0);
        when(credentialsDao.findByLogin("login")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.login("login", "password");
        });

        assertThat(exception.getStatusCode()).isEqualTo(401);
    }

    @Test
    void findAllShouldReturnAllUsers() {
        when(userDao.findAll()).thenReturn(entities);
        assertThat(userService.findAll()).isEqualTo(entities);
    }

    @Test
    void findAllByPageableShouldReturnLimitUsers() {
        Page<E> page = new PageImpl<>(entities.subList(0, 1));

        when(userDao.findAll()).thenReturn(entities);
        when(userDao.findAll(PageRequest.of(0, 1))).thenReturn(page);
        List<E> expected = userService.findAll(PageRequest.of(0, 1));

        assertThat(userService.findAll()).isEqualTo(entities);
        assertThat(expected).isEqualTo(entities.subList(0, 1));
    }

    @Test
    void updateShouldUpdateEntity() {
        when(userDao.findById(1L)).thenReturn(Optional.of(entities.get(0)));

        assertDoesNotThrow(() -> {
            userService.update(entities.get(0));
        });

        verify(userDao).save(entities.get(0));
    }

    @Test
    void findByIdShouldReturnEntity() {
        assertDoesNotThrow(() -> {
            userService.findById(entities.get(0).getId());
        });

        verify(userDao).findById(entities.get(0).getId());
    }

    @Test
    void deleteByIdShouldDeleteUserById() {
        assertDoesNotThrow(() -> {
            userService.deleteById(entities.get(0).getId());
        });

        verify(userDao).deleteById(entities.get(0).getId());
    }

    @Test
    void addToCourseShouldAddUserToCourse() {
        Course course = new Course();
        course.setId(1L);

        assertDoesNotThrow(() -> {
            userService.addToCourse(course, entities.get(0));
        });
    }

    @Test
    void deleteFromCourseShouldDeleteUserFromCourse() {
        Course course = new Course();
        course.setId(1L);

        assertDoesNotThrow(() -> {
            userService.deleteFromCourse(course, entities.get(0));
        });
    }

    @Test
    void approveAvatarShouldApproveAvatar() {
        assertDoesNotThrow(() -> {
            userService.approveAvatar(entities.get(0).getId());
        });
    }

    @Test
    void banAvatarShouldBanAvatar() {
        assertDoesNotThrow(() -> {
            userService.banAvatar(entities.get(0).getId());
        });
    }

    protected abstract void setEntity();
}
