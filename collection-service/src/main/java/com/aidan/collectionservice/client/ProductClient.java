package com.aidan.collectionservice.client;

import com.aidan.collectionservice.client.dto.AbstractProductDto;
import com.aidan.security.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;


@FeignClient(name = "product-service", url = "http://localhost:8087", configuration =  FeignClientConfig.class)
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    AbstractProductDto get(@PathVariable("id") UUID id);
}
