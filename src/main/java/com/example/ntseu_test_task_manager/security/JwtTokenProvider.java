package com.example.ntseu_test_task_manager.security;

import com.example.ntseu_test_task_manager.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-ms}")
    private Duration EXPIRATION_TIME;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole().name())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }
}
