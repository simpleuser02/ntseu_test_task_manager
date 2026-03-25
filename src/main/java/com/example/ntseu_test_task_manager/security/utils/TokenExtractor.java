package com.example.ntseu_test_task_manager.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public class TokenExtractor {
    private static final String BEARER_PREFIX = "Bearer ";

    private TokenExtractor() {}

    public static Optional<String> extract(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(header.substring(BEARER_PREFIX.length()));
    }
}
