package com.example.ntseu_test_task_manager.spec;

import com.example.ntseu_test_task_manager.entity.Task;
import com.example.ntseu_test_task_manager.entity.TaskStatus;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasAssignee(UUID assigneeId) {
        return (root, query, cb) ->
                assigneeId == null ? null : cb.equal(root.get("assignee").get("id"), assigneeId);
    }

    public static Specification<Task> hasAuthor(UUID authorId) {
        return (root, query, cb) ->
                authorId == null ? null : cb.equal(root.get("author").get("id"), authorId);
    }
}
