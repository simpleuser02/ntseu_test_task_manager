package com.example.ntseu_test_task_manager.security.exception;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
