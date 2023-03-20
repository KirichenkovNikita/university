package ru.systematic.university.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systematic.university.domain.Lesson;
import ru.systematic.university.service.LessonService;
import ru.systematic.university.service.domain.LessonReport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/lesson")
public class LessonController {
    private static final int PAGE_SIZE = 5;
    private final LessonService service;

    @GetMapping()
    public String index(Model model,
                        @PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        int lessonsCount = service.findAll().size();
        List<LessonReport> pageLessons = service.findAllReport(pageable);
        int pageCount = lessonsCount / PAGE_SIZE;

        if (lessonsCount % PAGE_SIZE != 0) {
            pageCount++;
        }

        model.addAttribute("url", "/lesson");
        model.addAttribute("page", pageCount);
        model.addAttribute("lessons", pageLessons);
        model.addAttribute("lesson", new Lesson());

        return "lesson/lessons";
    }

    @DeleteMapping(value = "/delete/{id}")
    private String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        service.deleteById(id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/addNew")
    private String addNew(@ModelAttribute Lesson lesson,
                          HttpServletRequest request) {
        service.add(lesson);

        return "redirect:" + request.getHeader("referer");
    }
}
