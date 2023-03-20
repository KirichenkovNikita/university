package ru.systematic.university.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.service.exception.TimetableException;
import ru.systematic.university.service.validator.anotation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Validator
public class LessonValidator implements EntityValidator<Lesson> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonValidator.class);

    @Override
    public void validate(Lesson entity) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Lesson>> violations = validator.validate(entity);

        for (ConstraintViolation<Lesson> violation : violations) {
            LOGGER.error(violation.getMessage());
            throw new TimetableException(violation.getMessage());
        }
    }
}
