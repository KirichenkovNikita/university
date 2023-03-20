package ru.systematic.university.service.user;

import lombok.Data;
import ru.systematic.university.domain.User;
import ru.systematic.university.service.validator.anotation.FieldMatch;
import ru.systematic.university.service.validator.anotation.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "passwordConfirm", message = "Password mismatch")
public abstract class UserRegistrationRequest<E extends User> {
    private static final int PASSWORD_LENGTH = 6;
    private static final String PASSWORD_MESSAGE = "Password must be more than 6 characters";

    @Email(message = "Invalid login format")
    @NotEmpty
    @NotNull
    @UniqueLogin
    private String login;

    @NotEmpty
    @Size(min = PASSWORD_LENGTH, message = PASSWORD_MESSAGE)
    private String password;

    @NotEmpty
    @Size(min = PASSWORD_LENGTH, message = PASSWORD_MESSAGE)
    private String passwordConfirm;

    @NotEmpty(message = "First name and Last name must be filled in")
    private String firstName;

    @NotEmpty(message = "First name and Last name must be filled in")
    private String lastName;

    public abstract E getEntity();
}
