package com.example.ntseu_test_task_manager.security.service;

import com.example.ntseu_test_task_manager.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private final JwtService jwtService;

    public Authentication authenticate(String token) {
        UserPrincipal principal = jwtService.getUserPrincipalFromToken(token);

        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
    }
}
