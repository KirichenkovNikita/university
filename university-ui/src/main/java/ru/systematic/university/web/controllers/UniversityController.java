package ru.systematic.university.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/university")
public class UniversityController {
    @GetMapping()
    public String index() {
        return "university/index";
    }
}
