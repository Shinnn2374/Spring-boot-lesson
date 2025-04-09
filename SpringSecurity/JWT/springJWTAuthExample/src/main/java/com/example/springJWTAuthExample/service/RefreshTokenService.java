package com.example.springJWTAuthExample.service;

import com.example.springJWTAuthExample.entite.RefreshToken;
import com.example.springJWTAuthExample.exception.RefreshTokenException;
import com.example.springJWTAuthExample.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long useId) {
        var refreshToken = RefreshToken.builder()
                .userId(useId)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis()))
                .token(UUID.randomUUID().toString()).build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken checkRefreshToken(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Repeat signin action!");
        }
        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
