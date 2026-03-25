package com.example.ntseu_test_task_manager.security.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthenticationToken(String token) {
        super(null, token);
    }
}
