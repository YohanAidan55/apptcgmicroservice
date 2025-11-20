package com.aidan.security.config;

import com.aidan.security.client.UserClient;
import com.aidan.security.client.UserLookupService;
import com.aidan.security.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig implements UserDetailsService {
    private final UserLookupService lookup;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("aaaa");
        UserDTO userEntity = lookup.findByEmail(email);
        log.info("bbbb");



        // Convertit les rôles en authorities Spring
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(userEntity.getRole().name()));

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .disabled(!userEntity.isEnabled())
                .build();
    }
}
