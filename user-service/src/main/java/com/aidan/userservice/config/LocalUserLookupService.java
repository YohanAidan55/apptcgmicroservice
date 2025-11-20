package com.aidan.userservice.config;

import com.aidan.security.client.UserLookupService;
import com.aidan.security.client.dto.UserDTO;
import com.aidan.userservice.user.repository.UserRepository;
import com.aidan.userservice.user.repository.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class LocalUserLookupService implements UserLookupService {

    private final UserRepository repo;
    private final UserMapper userMapper;

    public LocalUserLookupService(UserRepository repo, UserMapper userMapper) {
        this.repo = repo;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return repo.findByEmail(email)
                .map(userMapper::toDtoSecurity)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));
    }
}