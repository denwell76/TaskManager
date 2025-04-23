package com.backendproject.taskmanager.web;

import com.backendproject.taskmanager.domain.User;
import com.backendproject.taskmanager.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            model.addAttribute("error", "Please fill in all fields correctly");
            return "register";
        }
        try {
            System.out.println("Attempting to register: " + user.getUsername());
            userService.save(user);
            model.addAttribute("success", "Rekisteröinti onnistui! Voit nyt kirjautua sisään.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            System.out.println("Registration error: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            model.addAttribute("error", "Rekisteröinti epäonnistui: " + e.getMessage());
            return "register";
        }
    }
}