package ru.systematic.university.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.systematic.university.service.AvatarService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/approve")
public class ApproveController {
    private final AvatarService avatarService;

    @GetMapping()
    public String avatar(Model model) {
        model.addAttribute("avatars", avatarService.getAvatars());
        return "approve/avatar";
    }

    @PostMapping("/approveAvatar")
    public String approveAvatar(@RequestParam String id,
                                @RequestParam String userType,
                                HttpServletRequest request) {

        avatarService.approveAvatar(id, userType);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/banAvatar")
    public String banAvatar(@RequestParam String id,
                                @RequestParam String userType,
                                HttpServletRequest request) {

        avatarService.banAvatar(id, userType);
        return "redirect:" + request.getHeader("referer");
    }
}
