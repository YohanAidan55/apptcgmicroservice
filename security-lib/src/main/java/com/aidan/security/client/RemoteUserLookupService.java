package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnMissingBean(UserLookupService.class)
public class RemoteUserLookupService implements UserLookupService {

    private final UserClient userClient;

    public RemoteUserLookupService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDTO findByEmail(String email) {
        try {
            return userClient.getByEmail(email);
        } catch (Exception e) {
            log.error("Error while looking up user", e);
            return null;
        }
    }
}
