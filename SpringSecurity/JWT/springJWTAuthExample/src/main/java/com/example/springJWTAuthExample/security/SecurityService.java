package com.example.springJWTAuthExample.security;

import com.example.springJWTAuthExample.repository.UserRepository;
import com.example.springJWTAuthExample.security.jwt.JwtUtils;
import com.example.springJWTAuthExample.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



}
