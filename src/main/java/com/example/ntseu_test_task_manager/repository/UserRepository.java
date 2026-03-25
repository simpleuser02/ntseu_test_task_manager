package com.example.ntseu_test_task_manager.repository;

import com.example.ntseu_test_task_manager.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
    boolean existsUserByEmail(String email);

}
