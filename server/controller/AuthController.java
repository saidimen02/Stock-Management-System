package com.stockmanagement.server.controller;

import com.stockmanagement.server.JwtService;
import com.stockmanagement.server.dto.LoginRequest;
import com.stockmanagement.server.dto.LoginResponse;
import com.stockmanagement.server.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.getUsername() == null ||
            request.getPassword() == null ||
            request.getUsername().isBlank() ||
            request.getPassword().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Username and password are required"));
        }

        return userService.authenticate(
                request.getUsername(),
                request.getPassword()
        )
        .<ResponseEntity<?>>map(user -> {

            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            LoginResponse response = new LoginResponse(
                    token,
                    user.getUsername(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(response);
        })
        .orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message",
                                "Invalid username or password")));
    }
}