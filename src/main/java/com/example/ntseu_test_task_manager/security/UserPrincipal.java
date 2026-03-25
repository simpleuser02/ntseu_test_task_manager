package com.example.ntseu_test_task_manager.security;

import com.example.ntseu_test_task_manager.entity.UserRole;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final UUID id;
    private final String userName;
    private final String password;
    private final UserRole role;

    @Override
    public String getUsername() {
        return userName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" +role.name()));
    }

}
