package ru.systematic.university.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.systematic.university.domain.Student;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserRegistrationRequestValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void shouldReturnNoErrorsWhenRequestCorrect() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("123456");
        request.setPasswordConfirm("123456");
        request.setFirstName("Nikita");
        request.setLastName("Kirichenkov");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnErrorWhenRequestLoginIncorrect() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir");
        request.setPassword("123456");
        request.setPasswordConfirm("123456");
        request.setFirstName("Nikita");
        request.setLastName("Kirichenkov");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("Invalid login format");
    }

    @Test
    void shouldReturnErrorWhenRequestPasswordLengthIncorrect() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("12345");
        request.setPasswordConfirm("12345");
        request.setFirstName("Nikita");
        request.setLastName("Kirichenkov");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("Password must be more than 6 characters");
    }

    @Test
    void shouldReturnErrorWhenRequestPasswordMismatch() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("123456");
        request.setPasswordConfirm("1234567");
        request.setFirstName("Nikita");
        request.setLastName("Kirichenkov");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("Password mismatch");
    }

    @Test
    void shouldReturnErrorWhenRequestFirstNameEmpty() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("123456");
        request.setPasswordConfirm("123456");
        request.setFirstName("");
        request.setLastName("Kirichenkov");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("First name and Last name must be filled in");
    }

    @Test
    void shouldReturnErrorWhenRequestLastNameEmpty() {
        UserRegistrationRequest<Student> request = new StudentRegistrationRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("123456");
        request.setPasswordConfirm("123456");
        request.setFirstName("Nikita");
        request.setLastName("");
        Set<ConstraintViolation<UserRegistrationRequest<Student>>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("First name and Last name must be filled in");
    }
}
