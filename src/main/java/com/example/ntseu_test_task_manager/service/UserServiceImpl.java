package com.example.ntseu_test_task_manager.service;

import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.exception.UserNotFoundException;
import com.example.ntseu_test_task_manager.mapper.UserMapper;
import com.example.ntseu_test_task_manager.repository.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public void save(UserRequestDto userRequestDTO) {
        log.info("Attempting to save new user with email={}", userRequestDTO.email());
        if (userRepository.existsUserByEmail(userRequestDTO.email())) {
            log.warn("Registration failed: email={} already exists", userRequestDTO.email());
            throw new IllegalStateException("Email already exists");
        }

        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);
        log.info("User registered successfully: id={}, email={}", saved.getId(), saved.getEmail());
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(UUID id) {
        log.debug("Finding user by id={}", id);
        return userRepository.findById(id).orElseThrow(()-> {
            log.debug("Finding user by id={}", id);
            return new UserNotFoundException("User not found");
        });

    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public User findByUsername(String username) {
        log.debug("Finding user by username={}", username);
        return userRepository.findUserByUsername(username).orElseThrow(() -> {
           log.warn("User not found by username={}", username);
           return new UserNotFoundException("Can not find user");
        });
    }

    @Override
    public User getReferenceById(UUID id) {
        return userRepository.getReferenceById(id);
    }
}
