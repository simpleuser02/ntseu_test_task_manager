package com.example.ntseu_test_task_manager.dto;

import com.example.ntseu_test_task_manager.entity.TaskPriority;
import com.example.ntseu_test_task_manager.entity.TaskStatus;
import com.example.ntseu_test_task_manager.entity.User;
import java.time.LocalDateTime;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority taskPriority,
        User author,
        User assignee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

}
