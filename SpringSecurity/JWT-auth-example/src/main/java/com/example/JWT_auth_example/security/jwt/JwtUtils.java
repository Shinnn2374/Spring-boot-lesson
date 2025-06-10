package com.example.JWT_auth_example.security.jwt;

import com.example.JWT_auth_example.security.AppUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    public String generateJwtToken(AppUserDetails userDetails) {
        return generateJwtTokenFromUsernam(userDetails.getUsername() );
    }

    private String generateJwtTokenFromUsernam(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
        }catch (SignatureException e){
            log.error("Invalid signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Expired token: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Unsupported token: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Token is empty: {}", e.getMessage());
        }
    }
}
