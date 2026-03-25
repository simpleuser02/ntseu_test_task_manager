package com.example.ntseu_test_task_manager.dto;

import com.example.ntseu_test_task_manager.entity.TaskPriority;
import com.example.ntseu_test_task_manager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record TaskRequestDto(
        @NotBlank(message = "Title must not be empty")
        @Size(max = 255, message = "Title must be <= 255 chars")
        String title,
        @Size(max = 4000, message = "Description too long")
        String description,
        TaskStatus status,
        TaskPriority priority,
        UUID assigneeId
) {

}
