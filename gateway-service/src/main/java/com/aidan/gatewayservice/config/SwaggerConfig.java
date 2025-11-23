package com.aidan.gatewayservice.config;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    private final DiscoveryClient discoveryClient;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(@Lazy DiscoveryClient discoveryClient,
                         SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.discoveryClient = discoveryClient;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostConstruct
    public void init() {
        Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

        // Récupérer tous les services enregistrés dans Eureka
        discoveryClient.getServices().forEach(serviceName -> {
            // Exclure le gateway lui-même et eureka-server
            if (!serviceName.equalsIgnoreCase("gateway-service")
                    && !serviceName.equalsIgnoreCase("discovery-server")) {

                SwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                        new SwaggerUiConfigProperties.SwaggerUrl();
                swaggerUrl.setName(serviceName);
                swaggerUrl.setUrl("/" + serviceName.toLowerCase() + "/v3/api-docs");
                urls.add(swaggerUrl);
            }
        });

        swaggerUiConfigProperties.setUrls(urls);
    }
}