package com.example.ntseu_test_task_manager.spec;

import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.filter.TaskFilter;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecificationBuilder {

    public static Specification<Task> build(TaskFilter filter) {
        return Specification.where(TaskSpecifications.hasStatus(filter.getStatus()))
                .and(TaskSpecifications.hasAssignee(filter.getAssigneeId()))
                .and(TaskSpecifications.hasAuthor(filter.getAuthorId()));
    }
}
