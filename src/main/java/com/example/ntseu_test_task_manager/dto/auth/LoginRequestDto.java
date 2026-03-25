package com.example.ntseu_test_task_manager.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password
) { }

