package com.example.ntseu_test_task_manager.facade;

import com.example.ntseu_test_task_manager.dto.TaskRequestDto;
import com.example.ntseu_test_task_manager.dto.TaskResponseDto;
import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.entity.UserRole;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import com.example.ntseu_test_task_manager.mapper.TaskMapper;
import com.example.ntseu_test_task_manager.security.UserPrincipal;
import com.example.ntseu_test_task_manager.service.TaskService;
import com.example.ntseu_test_task_manager.service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final EntityManager entityManager;

    public TaskResponseDto create(TaskRequestDto dto, UserPrincipal userPrincipal) {
        User author = userService.findByUsername(userPrincipal.getUsername());
        log.debug("Creating task for author {}", author.getUsername());
        User assignee = userService.findById(dto.assigneeId());
        Task task = taskMapper.toEntity(dto);

        if (dto.assigneeId() != null) {
            task.setAssignee(entityManager.getReference(User.class, dto.assigneeId()));
        } else {
            task.setAssignee(null);
        }

        task.setAuthor(author);
        task.setAssignee(assignee);

        return taskMapper.toDto(taskService.save(task));
    }

    public TaskResponseDto update(Long id, TaskRequestDto dto, UserPrincipal userPrincipal) {
        log.debug("Facade update: task {} by user {}", id, userPrincipal.getUsername());
        boolean isAdmin = userPrincipal.getRole() == UserRole.ADMIN;

        Task task = taskService.findById(id);

        if (!isAdmin && !task.getAuthor().getId().equals(userPrincipal.getId())) {
            log.warn("User {} tried to update task {} without permission", userPrincipal.getUsername(), id);
            throw new AccessDeniedException("You can't update this task");
        }

        if (isAdmin && dto.assigneeId() != null) {
            User assignee = userService.getReferenceById(dto.assigneeId());
            task.setAssignee(assignee);
        }

        task = taskService.updateTask(task, dto);
        log.info("Task {} successfully updated", id);
        return taskMapper.toDto(task);
    }

    public Page<TaskResponseDto> findAll(TaskFilter filter, Pageable pageable, UserPrincipal userPrincipal) {
        log.debug("Returning tasks to user {}", userPrincipal.getUsername());
        Page<Task> tasks = taskService.findAll(filter, pageable);

        return tasks.map(taskMapper::toDto);
    }

    public TaskResponseDto findById(Long id, UserPrincipal userPrincipal) {
        Task task = taskService.findById(id);
        log.debug("Returning task {} to user {}", id, userPrincipal.getUsername());
        return taskMapper.toDto(task);
    }

    public void delete(Long id, UserPrincipal principal) {
        Task task = taskService.findById(id);

        if (!principal.getRole().equals(UserRole.ADMIN) &&
                !task.getAuthor().getId().equals(principal.getId())) {
            log.warn("User {} tried to delete task {} without permission", principal.getUsername(), id);
            throw new AccessDeniedException("Forbidden");
        }

       taskService.deleteById(id);
        log.info("Task {} was deleted", id);
    }
}
