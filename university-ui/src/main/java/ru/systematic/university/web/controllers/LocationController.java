package ru.systematic.university.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.systematic.university.domain.ClassRoom;
import ru.systematic.university.service.ClassRoomService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/location")
public class LocationController {
    ClassRoomService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("locations", service.findAll());
        model.addAttribute("error", "");
        model.addAttribute("location", new ClassRoom());

        return "location/locations";
    }

    @DeleteMapping(value = "/delete/{id}")
    private String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        service.deleteById(id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/addNew")
    private String addNew(@ModelAttribute ClassRoom location,
                          HttpServletRequest request) {
        service.add(location);

        return "redirect:" + request.getHeader("referer");
    }
}
