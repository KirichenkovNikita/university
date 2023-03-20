package ru.systematic.university.service.validator;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.systematic.university.dao.CredentialsDao;
import ru.systematic.university.service.validator.anotation.UniqueLogin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@NoArgsConstructor
public class UniqueLoginChecker implements ConstraintValidator<UniqueLogin, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueLoginChecker.class);
    private CredentialsDao credentialsDao;

    public UniqueLoginChecker(CredentialsDao credentialsDao) {
        this.credentialsDao = credentialsDao;
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;

        try {
            result = !credentialsDao.findByLogin(s).isPresent();
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        }

        return result;
    }
}
