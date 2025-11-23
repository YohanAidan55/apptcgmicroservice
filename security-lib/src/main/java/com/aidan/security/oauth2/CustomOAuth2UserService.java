package com.aidan.security.oauth2;


import com.aidan.security.client.UserClient;
import com.aidan.security.client.dto.AuthenticationProvider;
import com.aidan.security.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserClient userClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String userName = (String) attributes.get("name");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        // Essaie de récupérer l'utilisateur via le user-service
        try {
            userClient.getByEmail(email);
        } catch (Exception ignored) {
            // si l'appel échoue ou que l'utilisateur n'existe pas, on tente de le créer
            try {
                UserDTO toCreate = new UserDTO();
                toCreate.setEmail(email);
                toCreate.setUserName(userName);
                toCreate.setFirstName(firstName);
                toCreate.setLastName(lastName);
                toCreate.setProvider(AuthenticationProvider.GOOGLE);
                // pas de mot de passe => compte OAuth2
                userClient.register(toCreate);
            } catch (Exception ignored2) {
                // si la création échoue, on continue la connexion OAuth2 quand même
            }
        }

        // Retourne un OAuth2User Spring Security standard
        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }
}
