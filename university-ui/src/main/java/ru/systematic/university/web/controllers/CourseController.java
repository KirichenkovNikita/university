package ru.systematic.university.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systematic.university.domain.Course;
import ru.systematic.university.service.CourseService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {
    CourseService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("courses", service.findAll());
        model.addAttribute("error", "");

        return "course/courses";
    }

    @DeleteMapping(value = "/delete/{id}")
    private String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        service.deleteById(id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/addNew")
    private String addNew(@RequestParam String name,
                          @RequestParam String description,
                          HttpServletRequest request) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);

        service.add(course);

        return "redirect:" + request.getHeader("referer");
    }
}
