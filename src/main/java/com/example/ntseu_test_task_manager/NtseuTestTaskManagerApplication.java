package com.example.ntseu_test_task_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NtseuTestTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NtseuTestTaskManagerApplication.class, args);
    }

}
