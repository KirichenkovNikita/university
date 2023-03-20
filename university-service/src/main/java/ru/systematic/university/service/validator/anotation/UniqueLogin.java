package ru.systematic.university.service.validator.anotation;

import ru.systematic.university.service.validator.UniqueLoginChecker;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

@Constraint(validatedBy = UniqueLoginChecker.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueLogin {
    String message() default "Login already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
