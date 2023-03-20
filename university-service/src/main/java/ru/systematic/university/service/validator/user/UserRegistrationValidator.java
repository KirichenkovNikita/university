package ru.systematic.university.service.validator.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.exception.InvalidRegistrationException;
import ru.systematic.university.service.user.UserRegistrationRequest;
import ru.systematic.university.service.validator.EntityValidator;
import ru.systematic.university.service.validator.anotation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Validator
public class UserRegistrationValidator<E extends User> implements EntityValidator<UserRegistrationRequest<E>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationValidator.class);

    @Override
    public void validate(UserRegistrationRequest<E> request) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserRegistrationRequest<E>>> violations = validator.validate(request);

        for (ConstraintViolation<UserRegistrationRequest<E>> violation : violations) {
            LOGGER.error(violation.getMessage());
            throw new InvalidRegistrationException(violation.getMessage());
        }
    }
}
