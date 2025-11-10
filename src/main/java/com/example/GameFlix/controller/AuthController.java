package com.example.GameFlix.controller;

import com.example.GameFlix.model.User;
import com.example.GameFlix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "homePage";
    }

    //To view all users html
    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsersApi() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        String message = userService.registerUser(username, email, password);
        model.addAttribute("message", message);

        if (message.equals("User registered successfully")) {
            return "redirect:/login";
        }
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        String message = userService.loginUser(username, password);

        if (message.equals("Login successful")) {
            return "redirect:/"; // Redirect to homepage on success
        }
        model.addAttribute("error", message);
        return "login";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registerApi(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        String email = request.getOrDefault("email", username + "@gameflix.com");

        String message = userService.registerUser(username, email, password);
        HttpStatus status = message.equals("User registered successfully") ?
                HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(Map.of("message", message), status);
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginApi(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        String message = userService.loginUser(username, password);
        HttpStatus status = message.equals("Login successful") ?
                HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(Map.of("message", message), status);
    }





}