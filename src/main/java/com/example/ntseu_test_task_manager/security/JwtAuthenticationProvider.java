package com.example.ntseu_test_task_manager.security;

import com.example.ntseu_test_task_manager.security.service.JwtAuthenticationService;
import com.example.ntseu_test_task_manager.security.utils.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtAuthenticationService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        try {
            return authService.authenticate(token);
        } catch (Exception ex) {
            log.warn("JWT authentication failed: {}", ex.getMessage());
            throw new BadCredentialsException("Invalid JWT token", ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
