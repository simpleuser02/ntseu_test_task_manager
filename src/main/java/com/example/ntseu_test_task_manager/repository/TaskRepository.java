package com.example.ntseu_test_task_manager.repository;

import com.example.ntseu_test_task_manager.entity.Task;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @EntityGraph(attributePaths = {"author", "assignee"})
    @Override
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

    @EntityGraph("Task.author-assignee")
    Optional<Task> findTaskById(Long id);
}
