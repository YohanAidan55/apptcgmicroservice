package com.aidan.security.client;

import com.aidan.security.client.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
@ConditionalOnMissingBean(UserClient.class)
public class WebClientUserClient implements UserClient {

    private final WebClient webClient;

    @Autowired
    public WebClientUserClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public UserDTO getByEmail(String email) {
        try {
            return webClient.get()
                    .uri("/api/users/by-email?email={email}", email)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            log.info(ex.getMessage());
            return null;// user not found
        }
    }
}
