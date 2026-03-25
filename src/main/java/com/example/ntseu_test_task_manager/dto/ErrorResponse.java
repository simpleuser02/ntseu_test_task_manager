package com.example.ntseu_test_task_manager.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Instant timestamp;
}
