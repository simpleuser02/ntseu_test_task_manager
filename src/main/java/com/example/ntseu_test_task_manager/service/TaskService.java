package com.example.ntseu_test_task_manager.service;

import com.example.ntseu_test_task_manager.dto.TaskRequestDto;
import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task save(Task task);
    Task findById(Long id);
    Task updateTask(Task task, TaskRequestDto taskRequestDto);
    void deleteById(Long id);
    Page<Task> findAll(TaskFilter taskFilter, Pageable pageable);
}
