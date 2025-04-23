package com.backendproject.taskmanager;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backendproject.taskmanager.domain.UserRepository;
import com.backendproject.taskmanager.domain.User;

@SpringBootApplication
public class TaskmanagerApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Insert test user only if it doesn't exist
        if (userRepository.findByUsername("testuser") == null) {
            User user = new User();
            user.setUsername("testuser");
            user.setPassword(passwordEncoder.encode("salasana123"));
            userRepository.save(user);
        }
    }
}