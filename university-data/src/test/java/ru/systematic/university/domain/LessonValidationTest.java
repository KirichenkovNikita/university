package ru.systematic.university.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LessonValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldReturnNoErrorsWhenLessonCorrect() {
        Lesson lesson = new Lesson();
        lesson.setStartTime(LocalDateTime.MIN);
        lesson.setEndTime(LocalDateTime.MAX);
        Set<ConstraintViolation<Lesson>> violations = validator.validate(lesson);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnErrorWhenLessonTimeIncorrect() {
        Lesson lesson = new Lesson();
        lesson.setStartTime(LocalDateTime.MAX);
        lesson.setEndTime(LocalDateTime.MIN);
        Set<ConstraintViolation<Lesson>> violations = validator.validate(lesson);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("The start date must be earlier than the end date");
    }
}
