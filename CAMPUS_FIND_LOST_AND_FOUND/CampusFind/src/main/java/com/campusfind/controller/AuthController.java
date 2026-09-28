package com.campusfind.controller;

import com.campusfind.dto.LoginRequest;
import com.campusfind.dto.RegisterRequest;
import com.campusfind.entity.User;
import com.campusfind.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}
