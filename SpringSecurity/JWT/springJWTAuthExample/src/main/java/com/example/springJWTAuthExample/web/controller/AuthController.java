package com.example.springJWTAuthExample.web.controller;

import com.example.springJWTAuthExample.repository.UserRepository;
import com.example.springJWTAuthExample.security.SecurityService;
import com.example.springJWTAuthExample.web.model.AuthResponse;
import com.example.springJWTAuthExample.web.model.LoginRequest;
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

    private final UserRepository userRepository;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }
}
