package com.example.jwt_auth_example.web.controller;

import com.example.jwt_auth_example.excaption.AlreadyExistException;
import com.example.jwt_auth_example.repository.UserRepository;
import com.example.jwt_auth_example.security.SecurityService;
import com.example.jwt_auth_example.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(securityService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistException("Username is already in exist");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException("Email is already in exist");
        }

        securityService.register(request);
        return ResponseEntity.ok(new SimpleResponse("User created"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(securityService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return ResponseEntity.ok(new SimpleResponse("User logout, username is - " + userDetails.getUsername()));
    }


}
