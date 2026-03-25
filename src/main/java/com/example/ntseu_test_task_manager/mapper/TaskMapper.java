package com.example.ntseu_test_task_manager.mapper;

import com.example.ntseu_test_task_manager.dto.TaskRequestDto;
import com.example.ntseu_test_task_manager.dto.TaskResponseDto;
import com.example.ntseu_test_task_manager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskRequestDto dto);

    TaskResponseDto toDto(Task task);



}
