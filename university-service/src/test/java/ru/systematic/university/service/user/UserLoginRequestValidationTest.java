package ru.systematic.university.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginRequestValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldReturnNoErrorsWhenRequestCorrect() {
        UserLoginRequest request = new UserLoginRequest();
        request.setLogin("gnikkir@gmail.com");
        request.setPassword("123456");
        Set<ConstraintViolation<UserLoginRequest>> violations = validator.validate(request);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnErrorWhenRequestLoginIncorrect() {
        UserLoginRequest request = new UserLoginRequest();
        request.setLogin("gnikkir");
        request.setPassword("123456");
        Set<ConstraintViolation<UserLoginRequest>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.stream()
                .findFirst().get()
                .getMessage())
                .isEqualTo("Invalid login format");
    }
}
