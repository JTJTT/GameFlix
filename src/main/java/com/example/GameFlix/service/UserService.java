package com.example.GameFlix.service;

import com.example.GameFlix.model.User;
import com.example.GameFlix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return "User registered successfully";
    }

    // Overloaded method for API (without explicit email)
    public String registerUser(String username, String password) {
        return registerUser(username, username + "@gameflix.com", password);
    }

    public String loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return "Login successful";
        }
        return "Invalid username or password";
    }
}

