package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;

public interface UserLookupService {
    /**
     * Retourne null si pas trouvé.
     */
    UserDTO findByEmail(String email);
}