package ru.systematic.university.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.systematic.university.domain.Credentials;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.exception.InvalidLoginException;
import ru.systematic.university.service.user.ProfessorRegistrationRequest;
import ru.systematic.university.service.user.StudentRegistrationRequest;;
import ru.systematic.university.service.user.UserLoginRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private ProfessorService professorService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(studentService, professorService)).build();
    }

    @Test
    void indexShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("authentication/login"));
    }

    @Test
    void logoutShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(view().name("authentication/login"));
    }

    @Test
    void registerShouldReturnCorrectView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name("authentication/registration"));
    }

    @Test
    void userLogInShouldReturnCorrectControllerWhenStudentCorrect() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin("login");
        credentials.setPassword("password");
        UserLoginRequest request = new UserLoginRequest();
        request.setLogin("login");
        request.setPassword("password");


        when(studentService.login(credentials.getLogin(), credentials.getPassword())).thenReturn(credentials);
        mockMvc.perform(post("/userLogIn")
                .flashAttr("user", request))
                .andExpect(view().name("university/index"));
    }

    @Test
    void userLogInShouldThrowExceptionWhenStudentIncorrect() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setLogin("login");
        credentials.setPassword("password");
        UserLoginRequest request = new UserLoginRequest();
        request.setLogin("login");
        request.setPassword("password");

        when(studentService.login(credentials.getLogin(), credentials.getPassword())).thenThrow(new InvalidLoginException("Error occurred"));
        mockMvc.perform(post("/userLogIn")
                .flashAttr("user", request))
                .andExpect(model().attribute("error", "Error occurred"))
                .andExpect(view().name("authentication/login"));
    }

    @Test
    void studentSignUpShouldReturnCorrectControllerWhenStudentCorrect() throws Exception {
        StudentRegistrationRequest request = new StudentRegistrationRequest();
        request.setLogin("login");
        request.setPassword("password");
        request.setPasswordConfirm("password");

        mockMvc.perform(post("/studentSignUp")
                .flashAttr("student", request))
                .andExpect(view().name("university/index"));
    }

    @Test
    void studentSignUpShouldThrowExceptionWhenStudentIncorrect() throws Exception {
        StudentRegistrationRequest request = new StudentRegistrationRequest();
        request.setLogin("login");
        request.setPassword("password");
        request.setPasswordConfirm("password");

        when(studentService.register(request)).thenThrow(new InvalidLoginException("Error occurred"));
        mockMvc.perform(post("/studentSignUp")
                .flashAttr("student", request))
                .andExpect(model().attribute("studentError", "Error occurred"))
                .andExpect(view().name("authentication/registration"));
    }

    @Test
    void professorSignUpShouldReturnCorrectControllerWhenProfessorCorrect() throws Exception {
        ProfessorRegistrationRequest request = new ProfessorRegistrationRequest();
        request.setLogin("login");
        request.setPassword("password");
        request.setPasswordConfirm("password");

        mockMvc.perform(post("/professorSignUp")
                .flashAttr("professor", request))
                .andExpect(view().name("university/index"));
    }

    @Test
    void professorSignUpShouldThrowExceptionWhenProfessorIncorrect() throws Exception {
        ProfessorRegistrationRequest request = new ProfessorRegistrationRequest();
        request.setLogin("login");
        request.setPassword("password");
        request.setPasswordConfirm("password");

        when(professorService.register(request)).thenThrow(new InvalidLoginException("Error occurred"));
        mockMvc.perform(post("/professorSignUp")
                .flashAttr("professor", request))
                .andExpect(model().attribute("professorError", "Error occurred"))
                .andExpect(view().name("authentication/registration"));
    }
}
