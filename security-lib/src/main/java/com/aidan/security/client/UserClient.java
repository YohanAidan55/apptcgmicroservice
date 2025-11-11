package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",url = "${user.service.url:http://localhost:8082}", path = "/api/users")
public interface UserClient {

    @GetMapping("/by-email")
    UserDTO getByEmail(@RequestParam("email") String email);
}
