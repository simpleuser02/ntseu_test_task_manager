package com.example.ntseu_test_task_manager.security;

import com.example.ntseu_test_task_manager.entity.User;
import com.example.ntseu_test_task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findUserByUsername(email).orElseThrow(() -> new UsernameNotFoundException("Can not find user"));
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
