package com.example.ntseu_test_task_manager.util;

public final class ApiUrls {
    private ApiUrls() {}

    public static final String AUTH_BASE = "/api/v1/auth";
    public static final String REGISTER = AUTH_BASE + "/register";
    public static final String LOGIN = AUTH_BASE + "/login";

    public static final String TASKS_BASE = "/api/v1/tasks";
    public static final String TASK_BY_ID = TASKS_BASE + "/{id}";
}
