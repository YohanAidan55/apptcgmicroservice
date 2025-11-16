package com.aidan.security.autoconfig;

import com.aidan.security.config.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.aidan.security.jwt.JwtService;
import com.aidan.security.client.WebClientUserClient;
import com.aidan.security.jwt.JwtAuthenticationFilter;
import com.aidan.security.oauth2.OAuth2SuccessHandler;
import com.aidan.security.oauth2.CustomOAuth2UserService;

/**
 * Auto-configuration to expose the security library beans to consuming Spring Boot apps.
 * This class imports the library's @Configuration/@Component classes so they are
 * available when Spring Boot auto-configuration is enabled in the application.
 */
@Configuration(proxyBeanMethods = false)
@Import({
        SecurityConfig.class,
        ClientConfig.class,
        ApplicationConfig.class,
        CorsConfig.class,
        SwaggerConfig.class,
        JwtService.class,
        WebClientUserClient.class,
        JwtAuthenticationFilter.class,
        OAuth2SuccessHandler.class,
        CustomOAuth2UserService.class,
        FeignClientConfig.class
})
public class SecurityAutoConfiguration {
}
