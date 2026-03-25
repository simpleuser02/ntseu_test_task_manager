package com.example.ntseu_test_task_manager.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import com.example.ntseu_test_task_manager.repository.TaskRepository;
import com.example.ntseu_test_task_manager.service.TaskServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void findAll_happyPath() {
        TaskFilter filter = new TaskFilter();
        Pageable pageable = PageRequest.of(0, 10);

        Task task = new Task();
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(page);

        Page<Task> result = taskService.findAll(filter, pageable);

        assertEquals(1, result.getContent().size());
        verify(taskRepository).findAll(any(Specification.class), eq(pageable));
    }
}
