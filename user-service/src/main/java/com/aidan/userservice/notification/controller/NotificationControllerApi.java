package com.aidan.userservice.notification.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/users/notification")
public interface NotificationControllerApi {

    @PostMapping("/register")
    ResponseEntity<String> sendRegistrationEmail(
            @RequestParam String email,
            @RequestParam String confirmationToken);

    @PostMapping("/reset-password")
    ResponseEntity<String> sendPasswordResetEmail(
            @RequestParam String email,
            @RequestParam String confirmationToken);
}


