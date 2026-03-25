package com.example.ntseu_test_task_manager.utils;

import com.example.ntseu_test_task_manager.security.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class SecurityTestUtils {

    public static RequestPostProcessor userPrincipal(UserPrincipal principal) {
        return request -> {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.setUserPrincipal(auth);
            return request;
        };
    }
}