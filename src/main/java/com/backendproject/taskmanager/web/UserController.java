package com.backendproject.taskmanager.web;

import com.backendproject.taskmanager.domain.User;
import com.backendproject.taskmanager.domain.UserRepository;
import com.backendproject.taskmanager.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

   

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Näytä rekisteröintisivu
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Käsittele rekisteröintilomake
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            // Käyttäjätunnus on jo olemassa
            return "redirect:/register?error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login?registered";
    }
        
    }

    

