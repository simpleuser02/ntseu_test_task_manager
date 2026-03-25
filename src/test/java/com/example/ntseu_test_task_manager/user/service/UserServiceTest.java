package com.example.ntseu_test_task_manager.user.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.mapper.UserMapper;
import com.example.ntseu_test_task_manager.repository.UserRepository;
import com.example.ntseu_test_task_manager.service.UserServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser_happyPath() {
        UserRequestDto dto = new UserRequestDto("user1", "user1@email.com", "password");

        User user = new User();
        user.setPassword(dto.password());

        when(userRepository.existsUserByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("hashedPassword");
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        assertDoesNotThrow(() -> userService.save(dto));

        verify(userRepository).save(user);
        verify(passwordEncoder).encode(dto.password());
        verify(userMapper).toEntity(dto);
    }

    @Test
    void findById_happyPath() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);
        assertEquals(id, result.getId());
    }
}
