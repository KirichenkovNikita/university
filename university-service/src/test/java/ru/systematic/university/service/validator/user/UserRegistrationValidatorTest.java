package ru.systematic.university.service.validator.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.exception.InvalidRegistrationException;
import ru.systematic.university.service.user.StudentRegistrationRequest;
import ru.systematic.university.service.user.UserRegistrationRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserRegistrationValidatorTest {
    protected UserRegistrationRequest<Student> correctUser;
    protected UserRegistrationRequest<Student> incorrectLoginUser;
    protected UserRegistrationRequest<Student> incorrectPasswordUser;
    protected UserRegistrationRequest<Student> mismatchPasswordUser;
    protected UserRegistrationRequest<Student> incorrectNameUser;
    protected UserRegistrationValidator<Student> validator;

    @BeforeEach
    void setData() {
        validator = new UserRegistrationValidator<>();
        correctUser = new StudentRegistrationRequest();
        correctUser.setLogin("gnikkir@gmail.ru");
        correctUser.setFirstName("Никита");
        correctUser.setLastName("Кириченков");
        correctUser.setPassword("1234567");
        correctUser.setPasswordConfirm("1234567");

        incorrectNameUser = new StudentRegistrationRequest();
        incorrectNameUser.setLogin("gnikkir@gmail.ru");
        incorrectNameUser.setPassword("1234567");
        incorrectNameUser.setPasswordConfirm("1234567");
        incorrectNameUser.setFirstName("");
        incorrectNameUser.setLastName("");

        incorrectPasswordUser = new StudentRegistrationRequest();
        incorrectPasswordUser.setLogin("gnikkir@gmail.ru");
        incorrectPasswordUser.setFirstName("Никита");
        incorrectPasswordUser.setLastName("Кириченков");
        incorrectPasswordUser.setPassword("1");
        incorrectPasswordUser.setPasswordConfirm("1");

        incorrectLoginUser = new StudentRegistrationRequest();
        incorrectLoginUser.setLogin("gnikkir");
        incorrectLoginUser.setFirstName("Никита");
        incorrectLoginUser.setLastName("Кириченков");
        incorrectLoginUser.setPassword("1234567");
        incorrectLoginUser.setPasswordConfirm("1234567");

        mismatchPasswordUser = new StudentRegistrationRequest();
        mismatchPasswordUser.setLogin("gnikkir@gmail.ru");
        mismatchPasswordUser.setFirstName("Никита");
        mismatchPasswordUser.setLastName("Кириченков");
        mismatchPasswordUser.setPassword("1234567");
        mismatchPasswordUser.setPasswordConfirm("123456");
    }

    @Test
    void validateShouldDoNothingWhenUserCorrect() {
        assertDoesNotThrow(() -> {
            validator.validate(correctUser);
        });
    }

    @Test
    void validateShouldDoThrowExceptionWhenUserNull() {
        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(incorrectLoginUser);
        });
        assertThat(exception.getMessage()).isEqualTo("Invalid login format");
    }

    @Test
    void validateShouldDoThrowExceptionWhenPasswordIncorrect() {
        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(incorrectPasswordUser);
        });
        assertThat(exception.getMessage()).isEqualTo("Password must be more than 6 characters");
    }

    @Test
    void validateShouldDoThrowExceptionWhenPasswordMismatch() {
        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(mismatchPasswordUser);
        });
        assertThat(exception.getMessage()).isEqualTo("Password mismatch");
    }

    @Test
    void validateShouldDoThrowExceptionWhenLastNameIncorrect() {
        incorrectNameUser.setFirstName("Никита");

        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(incorrectNameUser);
        });
        assertThat(exception.getMessage()).isEqualTo("First name and Last name must be filled in");
    }

    @Test
    void validateShouldDoThrowExceptionWhenFirstNameIncorrect() {
        incorrectNameUser.setLastName("Кириченков");

        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(incorrectNameUser);
        });
        assertThat(exception.getMessage()).isEqualTo("First name and Last name must be filled in");
    }

    @Test
    void validateShouldDoThrowExceptionWhenFullNameIncorrect() {
        InvalidRegistrationException exception = Assertions.assertThrows(InvalidRegistrationException.class, () -> {
            validator.validate(incorrectNameUser);
        });
        assertThat(exception.getMessage()).isEqualTo("First name and Last name must be filled in");
    }
}
