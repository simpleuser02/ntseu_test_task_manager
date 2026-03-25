package com.example.ntseu_test_task_manager.controller;

import com.example.ntseu_test_task_manager.dto.TaskRequestDto;
import com.example.ntseu_test_task_manager.dto.TaskResponseDto;
import com.example.ntseu_test_task_manager.entity.TaskStatus;
import com.example.ntseu_test_task_manager.facade.TaskFacade;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import com.example.ntseu_test_task_manager.security.UserPrincipal;
import com.example.ntseu_test_task_manager.util.ApiUrls;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TaskController {
    private final TaskFacade taskFacade;

    @PostMapping(ApiUrls.TASKS_BASE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("User {} creating task with title '{}'", userPrincipal.getUsername(), taskRequestDto.title());
        TaskResponseDto result = taskFacade.create(taskRequestDto, userPrincipal);
        log.info("Task {} created by user {}", result, result.author().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Get all tasks with optional filters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping(ApiUrls.TASKS_BASE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<TaskResponseDto>> getTasks(
            @Parameter(description = "Task status") @RequestParam(required = false) TaskStatus status,
            @Parameter(description = "Assignee ID") @RequestParam(required = false) UUID assigneeId,
            @Parameter(description = "Author ID") @RequestParam(required = false) UUID authorId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction: asc or desc") @RequestParam(defaultValue = "desc") String direction,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        log.info("User {} requested tasks", principal.getUsername());
        TaskFilter filter = new TaskFilter();
        filter.setStatus(status);
        filter.setAssigneeId(assigneeId);
        filter.setAuthorId(authorId);

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TaskResponseDto> tasks = taskFacade.findAll(filter, pageable, principal);
        log.info("Tasks returned to user {}", principal.getUsername());
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping(ApiUrls.TASK_BY_ID)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        log.info("User {} requested task {}", principal.getUsername(), id);
        TaskResponseDto taskResponseDto = taskFacade.findById(id, principal);
        log.info("Task {} returned to user {}", id, principal.getUsername());
        return ResponseEntity.ok(taskResponseDto);
    }


    @Operation(summary = "Update task (admin or author)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping(ApiUrls.TASK_BY_ID)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDto taskRequestDto, @AuthenticationPrincipal UserPrincipal principal) {
        log.info("User {} updating task {} with DTO {}", principal.getUsername(), id, taskRequestDto);
        TaskResponseDto updatedTask = taskFacade.update(id, taskRequestDto, principal);
        log.info("Task {} updated by user {}", id, principal.getUsername());
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Delete task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping(ApiUrls.TASK_BY_ID)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void deleteById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        taskFacade.delete(id, principal);
    }

}
