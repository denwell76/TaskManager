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
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Käyttäjätunnus on jo käytössä");
            return "register";
        }
    
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            model.addAttribute("error", "Rekisteröinti epäonnistui. Yritä uudelleen.");
            return "register";
        }
    
        return "redirect:/login?registered";
        
    }
}
    

