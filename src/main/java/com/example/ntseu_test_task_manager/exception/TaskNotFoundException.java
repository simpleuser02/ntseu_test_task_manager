package com.example.ntseu_test_task_manager.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
