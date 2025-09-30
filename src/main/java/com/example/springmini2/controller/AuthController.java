package com.example.springmini2.controller;

import com.example.springmini2.model.RegisterUser;
import com.example.springmini2.model.dto.request.LoginRequest;
import com.example.springmini2.service.impl.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakUserService keycloakUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUser request) {
        keycloakUserService.registerUser(request);
        return ResponseEntity.ok("User registered successfully in Keycloak!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        return keycloakUserService.login(login);
    }

}

