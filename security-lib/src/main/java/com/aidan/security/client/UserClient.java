package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;
import com.aidan.security.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "user-service",
    url = "${app.services.user-service.url}",
    path = "/api/users/auth",
    configuration = FeignClientConfig.class
)
public interface UserClient {

    @GetMapping("/by-email")
    UserDTO getByEmail(@RequestParam("email") String email);

    @PostMapping("/register")
    UserDTO register(@RequestBody UserDTO userDTO);
}
