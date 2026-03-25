package com.example.ntseu_test_task_manager.mapper;

import com.example.ntseu_test_task_manager.dto.UserRequestDto;
import com.example.ntseu_test_task_manager.entity.User;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequestDto dto);

    @BeforeMapping
    default void validate(UserRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto must not be null");
        }
        if (dto.username() == null || dto.username().isBlank()) {
            throw new IllegalArgumentException("username must not be blank");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new IllegalArgumentException("password must not be blank");
        }
    }
}

