package com.aidan.userservice.user.service;

import com.aidan.userservice.client.CollectionClient;
import com.aidan.userservice.client.dto.CreateCollectionRequest;
import com.aidan.userservice.client.dto.CollectionDto;
import com.aidan.userservice.exception.ApiException;
import com.aidan.userservice.exception.NotFoundException;
import com.aidan.userservice.notification.controller.NotificationControllerApi;
import com.aidan.security.jwt.JwtService;
import com.aidan.userservice.user.domain.dto.UserDTO;
import com.aidan.userservice.user.domain.enums.RoleEnum;
import com.aidan.userservice.user.domain.enums.AuthenticationProvider;
import com.aidan.userservice.user.repository.UserRepository;
import com.aidan.userservice.user.repository.entity.UserEntity;
import com.aidan.userservice.user.repository.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final NotificationControllerApi notificationControllerApi;
    private final JwtService jwtService;
    private final CollectionClient collectionClient; // Feign client (peut être proxyé)

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(userEntity -> {
                    if (userEntity.getPassword() == null) { // compte OAuth2
                        throw new ApiException("EMAIL_ALREADY_OAUTH2",
                                "Vous avez déjà un compte via Google. Connectez-vous via Google.");
                    } else { // compte classique
                        throw new ApiException("EMAIL_ALREADY_EXIST",
                                "Un utilisateur existe déjà avec cet e-mail."
                        );
                    }
                });

        UserEntity userEntity = userMapper.toEntity(userDTO);

        // Déterminer le provider ; par défaut INTERNAL
        AuthenticationProvider provider = userDTO.getProvider() == null ? AuthenticationProvider.INTERNAL : userDTO.getProvider();
        userEntity.setProvider(provider);

        if (provider == AuthenticationProvider.GOOGLE) {
            // Compte OAuth2 : pas de mot de passe, on crée le compte activé directement
            userEntity.setPassword(null);
            userEntity.setEnabled(true);
            userEntity.setRole(RoleEnum.ROLE_USER);
            // Pas d'email de confirmation pour les comptes OAuth
            UserEntity saved = userRepository.save(userEntity);

            // Créer une collection "main" pour l'utilisateur (le collection-service récupère l'userId depuis le contexte de sécurité)
            try {
                CreateCollectionRequest req = new CreateCollectionRequest("main");
                CollectionDto created = collectionClient.create(req);
                if (created != null && created.getId() != null) {
                    collectionClient.setFavorite(created.getId());
                }
            } catch (Exception e) {
                log.warn("Échec création collection 'main' pour user {}: {}", saved.getId(), e.getMessage());
            }

            return userMapper.toDto(saved);
        } else {
            // Inscription classique (INTERNAL)
            String password = userDTO.getPassword();
            if (password == null || password.isBlank()) {
                throw new ApiException("PASSWORD_REQUIRED", "Mot de passe requis pour une inscription interne");
            }

            userEntity.setPassword(passwordEncoder.encode(password));
            userEntity.setEnabled(false);
            userEntity.setRole(RoleEnum.ROLE_USER);
            String confirmedToken = jwtService.generateTokenEmail(userDTO.getEmail(), 5);
            notificationControllerApi.sendRegistrationEmail(userDTO.getEmail(), confirmedToken);
            return userMapper.toDto(userRepository.save(userEntity));
        }
    }

    @Transactional
    public UserDTO confirmToken(String token) {
        String email = jwtService.extractUsername(token);

        if (email == null || !jwtService.isEmailTokenValid(token, email)) {
            throw new IllegalArgumentException("Token invalide ou expiré");
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));
        userEntity.setEnabled(true);
        UserEntity saved = userRepository.save(userEntity);

        // Créer une collection "main" pour l'utilisateur (le collection-service récupère l'userId depuis le contexte de sécurité)
        try {
            CreateCollectionRequest req = new CreateCollectionRequest("main");
            CollectionDto created = collectionClient.create(req);
            if (created != null && created.getId() != null) {
                collectionClient.setFavorite(created.getId());
            }
        } catch (Exception e) {
            log.warn("Échec création collection 'main' pour user {}: {}", saved.getId(), e.getMessage());
        }

        return userMapper.toDto(saved);
    }

    @Transactional
    public void requestPasswordReset(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));

        String resetToken = jwtService.generateTokenEmail(email, 5);

        notificationControllerApi.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        String email = jwtService.extractUsername(token);

        if (!jwtService.isEmailTokenValid(token, email)) {
            throw new RuntimeException("Token invalide ou expiré");
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void setPassword(String email, String password) {
        var userOpt = userRepository.findByEmail(email);
        var user = userOpt.orElseThrow(() -> new ApiException("USER_NOT_FOUND", "Utilisateur non trouvé"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
