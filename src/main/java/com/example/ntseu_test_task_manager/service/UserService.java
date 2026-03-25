package com.example.ntseu_test_task_manager.service;

import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.entity.User;
import java.util.UUID;

public interface UserService {
    void save(UserRequestDto user);
    User findById(UUID id);
    User findByUsername(String username);
    User getReferenceById(UUID id);
}
