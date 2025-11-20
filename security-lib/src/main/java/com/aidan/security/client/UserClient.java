package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE", path = "/api/users/auth")
public interface UserClient {

    @GetMapping("/by-email")
    UserDTO getByEmail(@RequestParam("email") String email);
}
