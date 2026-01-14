package com.pikabike.controller;

import com.pikabike.model.User;
import com.pikabike.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> optionalUser = userService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .badRequest()
                .body(Map.of("error", "Invalid credentials"));
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }
}