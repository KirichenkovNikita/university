package ru.systematic.university.service.impl.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.systematic.university.dao.CourseDao;
import ru.systematic.university.dao.CredentialsDao;
import ru.systematic.university.dao.LessonDao;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.UserService;
import ru.systematic.university.service.exception.EntityNotFoundException;
import ru.systematic.university.service.validator.EntityValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public abstract class AbstractUserServiceImpl<E extends User> implements UserService<E> {
    protected static final String LOGGER_FORMAT =
            "The start date must be earlier than the end date: start - %s, end - %s";
    protected final String uploadPath;
    protected final PagingAndSortingRepository<E, Long> userDao;
    protected final CredentialsDao credentialsDao;
    protected final LessonDao lessonDao;
    protected final CourseDao courseDao;
    protected final PasswordEncoder encoder;
    protected final EntityValidator<MultipartFile> fileValidator;

    @Override
    public Credentials login(String login, String password) {
        return credentialsDao.findByLogin(login)
                .filter(x -> encoder.matches(password, x.getPassword()))
                .orElseThrow(() -> new EntityNotFoundException("Incorrect login or password", 401));
    }

    @Override
    public List<E> findAll() {
        return (List<E>) userDao.findAll();
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        Page<E> pages = userDao.findAll(pageable);
        List<E> result = new ArrayList<>();
        if (pages != null) {
            result.addAll(pages.getContent());
        }
        return result;
    }

    @Override
    public Optional<E> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void update(E entity) {
        Optional<E> oldEntityOptional = userDao.findById(entity.getId());
        E oldEntity = oldEntityOptional.get();
        entity.setAvatarApproved(false);

        if (entity.getLogin() == null) {
            entity.setLogin(oldEntity.getLogin());
        }
        if (entity.getPassword() == null) {
            entity.setPassword(oldEntity.getPassword());
        }

        if (entity.getAvatar() == null) {
            entity.setAvatar(oldEntity.getAvatar());
            entity.setAvatarApproved(true);
        }

        userDao.save(entity);
    }

    @Override
    public void update(E entity, MultipartFile file) throws IOException {
        if (file.getSize() != 0) {
            fileValidator.validate(file);

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + resultFilename));

            entity.setAvatar(resultFilename);
        }

        update(entity);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
