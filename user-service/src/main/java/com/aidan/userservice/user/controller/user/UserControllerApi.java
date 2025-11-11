package com.aidan.userservice.user.controller.user;

import com.aidan.userservice.user.domain.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping("/api/users")
public interface UserControllerApi {

    @GetMapping("/me")
    UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails);

    @GetMapping("/all")
    List<UserDTO> getAll();

    @GetMapping("/by-email")
    UserDTO getByEmail(@RequestParam String email);
}
