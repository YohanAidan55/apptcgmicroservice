package com.aidan.userservice.user.controller.user;

import com.aidan.userservice.user.domain.dto.UserDTO;
import com.aidan.userservice.user.repository.UserRepository;
import com.aidan.userservice.user.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser() {
        log.debug("Entrée dans /api/users/me");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       var  username = ((UserDetails) auth.getPrincipal()).getUsername();

        return userRepository.findByEmail(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + username));
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("=> /test called !");
        return "ok";
    }
}
