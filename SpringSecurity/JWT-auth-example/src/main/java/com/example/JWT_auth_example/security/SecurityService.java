package com.example.JWT_auth_example.security;

import com.example.JWT_auth_example.repository.UserRepository;
import com.example.JWT_auth_example.security.jwt.JwtUtils;
import com.example.JWT_auth_example.services.RefreshTokenService;
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

    privat
}
