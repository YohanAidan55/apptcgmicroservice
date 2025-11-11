package com.aidan.userservice.user.controller.user;

import com.aidan.userservice.user.domain.dto.UserDTO;
import com.aidan.userservice.user.repository.UserRepository;
import com.aidan.userservice.user.repository.mapper.UserMapper;
import com.aidan.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserDTO getCurrentUser(@org.springframework.security.core.annotation.AuthenticationPrincipal UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .map(userMapper::toDto)
                 .orElseThrow(() -> new IllegalStateException("User not found with email: " + userDetails.getUsername()));
    }

    public List<UserDTO> getAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    public UserDTO getByEmail(@RequestParam("email") String email) {
        return userMapper.toDto(userService.getByEmail(email));
    }
}
