package com.example.ntseu_test_task_manager.service;


import com.example.ntseu_test_task_manager.dto.TaskRequestDto;
import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.exception.TaskNotFoundException;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import com.example.ntseu_test_task_manager.repository.TaskRepository;
import com.example.ntseu_test_task_manager.spec.TaskSpecificationBuilder;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task save(Task task) {
       Task saved = taskRepository.saveAndFlush(task);
       log.debug("Task {} saved in DB", saved.getId());
       return saved;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "tasks", key = "#id")
    @Override
    public Task findById(Long id) {
        return taskRepository.findTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    @CacheEvict(value = "tasks", key = "#task.id")
    public Task updateTask(Task task, TaskRequestDto taskRequestDto) {
        log.debug("Patching task {} with DTO {}", task.getId(), taskRequestDto);
        Optional.ofNullable(taskRequestDto.title()).ifPresent(task::setTitle);
        Optional.ofNullable(taskRequestDto.description()).ifPresent(task::setDescription);
        Optional.ofNullable(taskRequestDto.status()).ifPresent(task::setStatus);
        Optional.ofNullable(taskRequestDto.priority()).ifPresent(task::setPriority);

        return taskRepository.save(task);
    }

    @Override
    @CacheEvict(value = "tasks", key = "#task.id")
    public void deleteById(Long id) {
        log.debug("Deleting task {}", id);
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "tasks", key = "#filter + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    @Override
    public Page<Task> findAll(TaskFilter filter, Pageable pageable) {
        Specification<Task> spec = TaskSpecificationBuilder.build(filter);
        return taskRepository.findAll(spec, pageable);
    }
}
