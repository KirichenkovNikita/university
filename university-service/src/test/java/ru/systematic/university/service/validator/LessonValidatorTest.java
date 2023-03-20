package ru.systematic.university.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.service.exception.TimetableException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LessonValidatorTest {
    private final LessonValidator validator = new LessonValidator();


    @Test
    void validateShouldDoNothingWhenLessonCorrect() {
        Lesson lesson = new Lesson();
        lesson.setStartTime(LocalDateTime.MIN);
        lesson.setEndTime(LocalDateTime.MAX);

        assertDoesNotThrow(() -> {
            validator.validate(lesson);
        });
    }

    @Test
    void validateShouldDoNothingWhenLessonIncorrect() {
        Lesson lesson = new Lesson();
        lesson.setStartTime(LocalDateTime.MAX);
        lesson.setEndTime(LocalDateTime.MIN);

        TimetableException exception = Assertions.assertThrows(TimetableException.class, () -> {
            validator.validate(lesson);
        });
        assertThat(exception.getMessage()).isEqualTo("The start date must be earlier than the end date");
    }
}
