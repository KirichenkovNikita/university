package ru.systematic.university.anotation;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Component
public class TimetableMatchValidator implements ConstraintValidator<TimetableMatch, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableMatchValidator.class);
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(final TimetableMatch constraintAnnotation) {
        startFieldName = constraintAnnotation.start();
        endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final LocalDateTime startTime = LocalDateTime.parse(BeanUtils.getProperty(value, startFieldName));
            final LocalDateTime endTime = LocalDateTime.parse(BeanUtils.getProperty(value, endFieldName));

            valid = startTime.isBefore(endTime);
        } catch (final Exception exception) {
            LOGGER.error(exception.getMessage());
        }

        return valid;
    }
}
