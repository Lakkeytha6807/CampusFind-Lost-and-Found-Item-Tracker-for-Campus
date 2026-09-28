package com.campusfind.service;

import com.campusfind.dto.LoginRequest;
import com.campusfind.dto.RegisterRequest;
import com.campusfind.entity.User;
import com.campusfind.exception.BadRequestException;
import com.campusfind.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BadRequestException("Email is already registered");
        }

        User user = new User(
                request.name(),
                request.email(),
                encoder.encode(request.password()),
                request.role()
        );
        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return user;
    }
}
