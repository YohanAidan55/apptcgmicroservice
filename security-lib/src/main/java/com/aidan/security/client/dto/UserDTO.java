package com.aidan.security.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private RoleEnum role;
    private String password;
}

