package com.aidan.userservice.client;

import com.aidan.userservice.client.dto.CreateCollectionRequest;
import com.aidan.userservice.client.dto.CollectionDto;
import com.aidan.security.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "COLLECTION-SERVICE", path = "/api/collections", configuration = FeignClientConfig.class)
public interface CollectionClient {

    @PostMapping
    CollectionDto create(@RequestBody CreateCollectionRequest req);

    @PostMapping("/{id}/favorite")
    CollectionDto setFavorite(@PathVariable("id") java.util.UUID id);
}
