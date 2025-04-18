package com.example.springJWTAuthExample.security;

import com.example.springJWTAuthExample.entite.RefreshToken;
import com.example.springJWTAuthExample.entite.User;
import com.example.springJWTAuthExample.exception.RefreshTokenException;
import com.example.springJWTAuthExample.repository.UserRepository;
import com.example.springJWTAuthExample.security.jwt.JwtUtils;
import com.example.springJWTAuthExample.service.RefreshTokenService;
import com.example.springJWTAuthExample.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }


    public void register(CreateUserRequest createUserRequest) {
        var user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();
        user.setRoles(createUserRequest.getRoles());
        userRepository.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String requestToken = refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByRefreshToken(requestToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                   User tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                           new RefreshTokenException("Exception try to get token for userId: " + userId));
                   String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());
                   return new RefreshTokenResponse(token,refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(requestToken ,"Refresh token not found"));
    }

    public void logout() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            Long userId = userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }
}
