package com.example.ntseu_test_task_manager.security.service;

import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.exception.UserNotFoundException;
import com.example.ntseu_test_task_manager.repository.UserRepository;
import com.example.ntseu_test_task_manager.security.JwtTokenParser;
import com.example.ntseu_test_task_manager.security.UserPrincipal;
import com.example.ntseu_test_task_manager.security.exception.JwtAuthenticationException;
import com.example.ntseu_test_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenParser tokenParser;
    private final UserService userService;

    public UserPrincipal getUserPrincipalFromToken(String token) {
        if (!tokenParser.isValid(token)) {
            throw new JwtAuthenticationException("Invalid JWT token");
        }

        String username = tokenParser.extractUserName(token);

        User user = userService.findByUsername(username);

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
