package com.example.ntseu_test_task_manager.auth;

import com.example.ntseu_test_task_manager.controller.AuthController;
import com.example.ntseu_test_task_manager.dto.auth.LoginRequestDto;
import com.example.ntseu_test_task_manager.dto.auth.LoginResponseDto;
import com.example.ntseu_test_task_manager.security.service.AuthService;
import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.service.UserService;
import com.example.ntseu_test_task_manager.util.ApiUrls;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Test
    void register_happyPath() throws Exception {
        UserRequestDto dto = new UserRequestDto("user1", "user1@email.com", "password");

        mockMvc.perform(post(ApiUrls.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void login_happyPath() throws Exception {
        LoginRequestDto dto = new LoginRequestDto("user1@email.com", "password");
        when(authService.login(dto)).thenReturn(new LoginResponseDto("dummy-jwt-token"));

        mockMvc.perform(post(ApiUrls.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
