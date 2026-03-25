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
/*@Service
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenParser tokenParser;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.startsWith("/api/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }


        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }

        String token = header.substring(7);
        if (!tokenParser.isValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userName = tokenParser.extractUserName(token);
        User user = userRepository.findUserByUsername(userName).orElseThrow(()-> new UserNotFoundException("Can not find user"));

        UserPrincipal principal = new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                       principal.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}*/
