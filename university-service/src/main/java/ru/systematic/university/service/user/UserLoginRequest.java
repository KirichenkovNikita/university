package ru.systematic.university.service.user;

import lombok.Data;
import ru.systematic.university.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserLoginRequest {

    @Email(message = "Invalid login format")
    @NotEmpty
    @NotNull
    private String login;

    @NotEmpty
    private String password;

    public User getEntity() {
        User user = new User();
        user.setLogin(getLogin());
        user.setPassword(getPassword());

        return user;
    }
}
