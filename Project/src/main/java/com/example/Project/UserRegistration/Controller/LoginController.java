package com.example.Project.UserRegistration.Controller;

import com.example.Project.UserRegistration.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email    = loginData.get("email");
        String password = loginData.get("password");

        try {
            String token = userService.login(email, password);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}