package com.example.ntseu_test_task_manager.security.filter;

import com.example.ntseu_test_task_manager.security.utils.JwtAuthenticationToken;
import com.example.ntseu_test_task_manager.security.utils.TokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;



@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if (isAuthPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenExtractor.extract(request).ifPresent(token -> {
            try {
                Authentication authRequest = new JwtAuthenticationToken(token);
                Authentication authResult = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authResult);

                log.debug("JWT authentication success");
            } catch (AuthenticationException ex) {
                log.warn("JWT authentication failed: {}", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        });

        filterChain.doFilter(request, response);
    }

    private boolean isAuthPath(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/v1/auth/");
    }
}
