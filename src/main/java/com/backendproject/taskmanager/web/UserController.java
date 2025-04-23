package com.backendproject.taskmanager.web;

import com.backendproject.taskmanager.domain.User;
import com.backendproject.taskmanager.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Näytä rekisteröintisivu
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Käsittele rekisteröintilomake
    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        try {
            userService.save(user);
            model.addAttribute("success", "Rekisteröinti onnistui! Voit nyt kirjautua sisään.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}