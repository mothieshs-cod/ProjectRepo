package com.example.Project.UserRegistration.Controller;

import com.example.Project.UserRegistration.Dto.RegisterRequest;
import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody RegisterRequest request) {
        try {
            String message = userService.updateProfile(request);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}