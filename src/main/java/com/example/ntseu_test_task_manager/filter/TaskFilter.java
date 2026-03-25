package com.example.ntseu_test_task_manager.filter;

import com.example.ntseu_test_task_manager.entity.TaskStatus;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TaskFilter {

    private TaskStatus status;
    private UUID assigneeId;
    private UUID authorId;
}
