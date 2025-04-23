package com.backendproject.taskmanager.domain.service;

import com.backendproject.taskmanager.domain.User;
import com.backendproject.taskmanager.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        System.out.println("Registering user: " + user.getUsername() + ", password: " + user.getPassword());
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username or password cannot be null");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        System.out.println("User saved: " + savedUser.getUsername() + ", id: " + savedUser.getId());
    }

    public User findByUsername(String username) {
        System.out.println("Finding user: " + username);
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}