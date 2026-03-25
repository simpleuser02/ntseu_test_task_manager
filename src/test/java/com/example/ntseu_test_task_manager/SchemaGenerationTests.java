package com.example.ntseu_test_task_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SchemaGenerationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldHaveUsersAndTasksTables() {
        Integer usersCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_NAME='USERS'",
                Integer.class
        );
        Integer tasksCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_NAME='TASKS'",
                Integer.class
        );

        assertThat(usersCount).isNotNull();
        assertThat(tasksCount).isNotNull();
        assertThat(usersCount).isGreaterThan(0);
        assertThat(tasksCount).isGreaterThan(0);
    }
}

