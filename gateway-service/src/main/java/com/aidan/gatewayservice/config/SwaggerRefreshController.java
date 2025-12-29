package com.aidan.gatewayservice.config;

import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class SwaggerRefreshController {

    private final DiscoveryClient discoveryClient;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerRefreshController(@Lazy DiscoveryClient discoveryClient,
                                    SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.discoveryClient = discoveryClient;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostMapping("/swagger-refresh")
    public String refreshSwagger() {
        Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

        discoveryClient.getServices().forEach(serviceName -> {
            if (!serviceName.equalsIgnoreCase("gateway-service")) {
                SwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new SwaggerUiConfigProperties.SwaggerUrl();
                swaggerUrl.setName(serviceName);
                swaggerUrl.setUrl("/" + serviceName.toLowerCase() + "/v3/api-docs");
                urls.add(swaggerUrl);
            }
        });

        swaggerUiConfigProperties.setUrls(urls);

        return "Swagger URLs updated: " + urls.size() + " services registered.";
    }
}
