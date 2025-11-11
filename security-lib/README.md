# security-lib

Librairie partagée pour sécurité (JWT, OAuth2 helpers) destinée à être utilisée par d'autres microservices.

Principales fonctionnalités:
- Génération et vérification de JWT (classe `JwtService`).
- Filtre d'authentification JWT (`JwtAuthenticationFilter`).
- Intégration OAuth2 (login via Google) et handler de succès (`OAuth2SuccessHandler`).
- Client découplé pour interagir avec le microservice `user` (`UserClient` + impl `WebClientUserClient`).

Configuration requise (dans `application.yml` ou `application.properties` du microservice consommateur):

- jwt.secret: la clé secrète HMAC (32+ caractères recommandés)
- jwt.expiration-ms: durée d'expiration en ms (optionnel, défaut 7 jours)
- user.service.base-url: URL du service utilisateur (par défaut http://localhost:8081)
- app.oauth2.redirect-url: URL frontend de redirection après OAuth2 login

Exemple minimal (application.yml):

```yaml
jwt:
  secret: "une-clee-longue-au-moins-32-caracteres-..."
  expiration-ms: 604800000

user:
  service:
    base-url: http://user-service:8080

app:
  oauth2:
    redirect-url: http://frontend:5173/oauth2/callback?token=
```

Comment consommer la lib:
1. Ajouter cette dépendance au pom du microservice consommateur (groupId/artifactId/version selon publication interne dans Nexus/Artifactory ou `install` local).
2. Définir les propriétés ci-dessus.
3. Si vous préférez Feign au lieu de WebClient, fournissez votre propre bean `UserClient` (implémentation basée sur Feign) — l'interface `UserClient` permet le découplage.

Bonnes pratiques:
- Ne stockez pas la clé `jwt.secret` en clair dans le repo; préférez Vault/Secrets manager ou variables d'environnement.
- La librairie ne persiste pas d'utilisateur: la logique de création/utilisateur reste dans le service `user`.

Alternatives:
- Remplacer `WebClientUserClient` par une implémentation Feign et activer `@EnableFeignClients` dans le microservice consommateur.

Support / développement:
- Java 17+, Spring Boot 3.x
- Tests: `mvn test` dans le projet (si vous avez Maven installé)


