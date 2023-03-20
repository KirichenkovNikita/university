package ru.systematic.university.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.systematic.university.domain.Professor;
import ru.systematic.university.domain.Student;
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.ProfessorService;
import ru.systematic.university.service.StudentService;
import ru.systematic.university.service.domain.LessonReport;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final String ICON_PATH = "/img/";
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final LessonService lessonService;
    private final String baseManIcon;

    public ProfileController(StudentService studentService, ProfessorService professorService,
                             LessonService lessonService, @Value("${man.base.img.name}") String baseManIcon) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.lessonService = lessonService;
        this.baseManIcon = baseManIcon;
    }

    @GetMapping("/student/{userId}")
    public String profileStudent(@PathVariable String userId, Model model) {
        String iconPath = baseManIcon;
        Optional<Student> studentOptional = studentService.findById(Long.valueOf(userId));
        if (!studentOptional.isPresent()) {
            return "profile/error";
        }

        Student student = studentOptional.get();

        if (student.getAvatar() != null && !student.getAvatar().isEmpty() && student.getAvatarApproved()) {
            iconPath = ICON_PATH + student.getAvatar();
        }

        List<LessonReport> lessons = studentService.getTimetableReport(student, LocalDateTime.MIN, LocalDateTime.MAX);
        model.addAttribute("studentUpdate", student);
        model.addAttribute("lessonsWeek", lessonService.getWeekTable(lessons));
        model.addAttribute("icon", iconPath);
        model.addAttribute("id", student.getId());
        model.addAttribute("lessons", lessons);
        return "profile/student";
    }

    @GetMapping("/professor/{userId}")
    public String profileProfessor(@PathVariable String userId, Model model) {
        String iconPath = baseManIcon;
        Optional<Professor> professorOptional = professorService.findById(Long.valueOf(userId));
        if (!professorOptional.isPresent()) {
            return "profile/error";
        }

        Professor professor = professorOptional.get();

        if (professor.getAvatar() != null && !professor.getAvatar().isEmpty() && professor.getAvatarApproved()) {
            iconPath = ICON_PATH + professor.getAvatar();
        }

        List<LessonReport> lessons = professorService.
                getTimetableReport(professor, LocalDateTime.MIN, LocalDateTime.MAX);
        model.addAttribute("professorUpdate", professor);
        model.addAttribute("lessonsWeek", lessonService.getWeekTable(lessons));
        model.addAttribute("icon", iconPath);
        model.addAttribute("id", professor.getId());
        model.addAttribute("lessons", lessons);
        return "profile/professor";
    }

    @PostMapping("/student/update")
    public String studentUpdate(@RequestParam("file") MultipartFile file,
                                @ModelAttribute("studentUpdate") Student student,
                                HttpServletRequest request) throws IOException {

        studentService.update(student, file);

        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/professor/update")
    public String professorUpdate(@RequestParam("file") MultipartFile file,
                                  @ModelAttribute("professorUpdate") Professor professor,
                                  HttpServletRequest request) throws IOException {

        professorService.update(professor, file);

        return "redirect:" + request.getHeader("referer");
    }
}
