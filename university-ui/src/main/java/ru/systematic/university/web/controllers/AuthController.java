package ru.systematic.university.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.user.ProfessorRegistrationRequest;
import ru.systematic.university.service.user.StudentRegistrationRequest;;
import ru.systematic.university.service.user.UserLoginRequest;

@Controller
@AllArgsConstructor
public class AuthController {
    private final StudentService studentService;
    private final ProfessorService professorService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("error", "");

        return "authentication/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("error", "");

        return "authentication/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("professor", new ProfessorRegistrationRequest());
        model.addAttribute("professorError", "");
        model.addAttribute("student", new StudentRegistrationRequest());
        model.addAttribute("studentError", "");
        return "authentication/registration";
    }

    @PostMapping("/userLogIn")
    public String studentLogIn(@ModelAttribute("user") UserLoginRequest user, Model model) {
        try {
            studentService.login(user.getLogin(), user.getPassword());
            return "university/index";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("user", new UserLoginRequest());
            return "authentication/login";
        }
    }

    @PostMapping("/studentSignUp")
    public String studentSignUp(@ModelAttribute("student") StudentRegistrationRequest student, Model model) {
        try {
            studentService.register(student);
            return "university/index";
        } catch (Exception ex) {
            model.addAttribute("studentError", ex.getMessage());
            model.addAttribute("professor", new ProfessorRegistrationRequest());
            model.addAttribute("student", new StudentRegistrationRequest());
            return "authentication/registration";
        }
    }

    @PostMapping("/professorSignUp")
    public String professorSignUp(@ModelAttribute("professor") ProfessorRegistrationRequest professor, Model model) {
        try {
            professorService.register(professor);
            return "university/index";
        } catch (Exception ex) {
            model.addAttribute("professorError", ex.getMessage());
            model.addAttribute("professor", new ProfessorRegistrationRequest());
            model.addAttribute("student", new StudentRegistrationRequest());
            return "authentication/registration";
        }
    }
}
