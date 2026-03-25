package com.example.ntseu_test_task_manager.security.config;

import com.example.ntseu_test_task_manager.security.JwtAuthenticationProvider;
import com.example.ntseu_test_task_manager.security.filter.JwtFilter;
import com.example.ntseu_test_task_manager.security.handler.CustomAccessDeniedHandler;
import com.example.ntseu_test_task_manager.security.handler.CustomAuthenticationEntryPoint;
import com.example.ntseu_test_task_manager.util.ApiUrls;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
            JwtAuthenticationProvider provider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(provider)
                .build();
    }

    @Bean
    public JwtFilter jwtFilter(AuthenticationManager authenticationManager) {
        return new JwtFilter(authenticationManager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ApiUrls.REGISTER,
                                        ApiUrls.LOGIN,
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                        .permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }








}
