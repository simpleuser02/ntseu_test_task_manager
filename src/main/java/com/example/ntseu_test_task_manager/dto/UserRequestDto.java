package com.example.ntseu_test_task_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "Username is required")
        String username,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {
}
