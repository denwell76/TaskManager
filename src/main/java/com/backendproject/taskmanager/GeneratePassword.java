package com.backendproject.taskmanager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "salasana123";
        String hash = encoder.encode(password);
        System.out.println(hash);
    }
}