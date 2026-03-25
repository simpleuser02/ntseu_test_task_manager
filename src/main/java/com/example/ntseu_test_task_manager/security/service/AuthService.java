package com.example.ntseu_test_task_manager.security.service;

import com.example.ntseu_test_task_manager.dto.auth.LoginRequestDto;
import com.example.ntseu_test_task_manager.dto.auth.LoginResponseDto;
import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.security.JwtTokenProvider;
import com.example.ntseu_test_task_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userService.findByUsername(loginRequestDto.username());

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        return new LoginResponseDto(jwtTokenProvider.generateToken(user));
    }

}
