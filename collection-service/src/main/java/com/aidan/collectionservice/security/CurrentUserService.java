package com.aidan.collectionservice.security;

import com.aidan.security.client.UserLookupService;
import com.aidan.security.client.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    private final UserLookupService userLookupService;

    public CurrentUserService(UserLookupService userLookupService) {
        this.userLookupService = userLookupService;
    }

    public UUID  getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return null;
        }
        String usernameOrEmail = auth.getName();
        UserDTO user = userLookupService.findByEmail(usernameOrEmail);
        if (user == null) return null;
        return user.getId();
    }
}

