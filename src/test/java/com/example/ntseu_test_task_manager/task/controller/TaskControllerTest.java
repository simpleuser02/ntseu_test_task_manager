package com.example.ntseu_test_task_manager.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ntseu_test_task_manager.controller.TaskController;
import com.example.ntseu_test_task_manager.dto.TaskResponseDto;
import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.entity.UserRole;
import com.example.ntseu_test_task_manager.facade.TaskFacade;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import com.example.ntseu_test_task_manager.security.UserPrincipal;
import com.example.ntseu_test_task_manager.util.ApiUrls;
import com.example.ntseu_test_task_manager.utils.SecurityTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskFacade taskFacade;

    @Test
    void getTasks_happyPath() throws Exception {
        TaskResponseDto dto = new TaskResponseDto(
                1L,
                "Task 1",
                "Description 1",
                null,
                null,
                null,
                null,
                null,
                null
        );
        UserPrincipal principal = new UserPrincipal(UUID.randomUUID(), "user1", "password", UserRole.USER);


        Page<TaskResponseDto> page = new PageImpl<>(List.of(dto));

        when(taskFacade.findAll(any(TaskFilter.class), any(), any(UserPrincipal.class)))
                .thenReturn(page);

        mockMvc.perform(get(ApiUrls.TASKS_BASE)
                        .with(SecurityTestUtils.userPrincipal(principal))
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createTask_happyPath() throws Exception {
        String requestJson = """
            {
                "title": "New Task",
                "description": "Task description",
                "status": "TODO"
            }
        """;

        TaskResponseDto response = new TaskResponseDto(
                1L,
                "New Task",
                "Task description",
                null, null, new User(), null, null, null
        );

        doReturn(response)
                .when(taskFacade)
                .create(any(), any());

        UserPrincipal principal = new UserPrincipal(UUID.randomUUID(), "user1", "password", UserRole.USER);

        mockMvc.perform(post(ApiUrls.TASKS_BASE)
                        .with(SecurityTestUtils.userPrincipal(principal))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }
}
