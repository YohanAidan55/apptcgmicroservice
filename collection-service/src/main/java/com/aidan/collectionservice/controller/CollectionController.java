package com.aidan.collectionservice.controller;

import com.aidan.collectionservice.controller.dto.CollectionDto;
import com.aidan.collectionservice.controller.dto.CreateCollectionRequest;
import com.aidan.collectionservice.service.CollectionService;
import com.aidan.collectionservice.security.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public CollectionDto create(@RequestBody CreateCollectionRequest req) {
        java.util.UUID userId = currentUserService.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated or not found");
        return collectionService.create(req, userId);
    }

    @GetMapping
    public List<CollectionDto> getAll() {
        java.util.UUID userId = currentUserService.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated or not found");
        return collectionService.getAll(userId);
    }

    @GetMapping("/{id}")
    public CollectionDto get(@PathVariable("id") UUID id) {
        return collectionService.get(id);
    }

    @PutMapping("/{id}")
    public CollectionDto update(@PathVariable("id") UUID id, @RequestBody CreateCollectionRequest req) {
        return collectionService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        collectionService.delete(id);
    }

    @PostMapping("/{id}/products/{productId}")
    public CollectionDto addProduct(@PathVariable("id") UUID id, @PathVariable ("productId") UUID productId) {
        return collectionService.addProduct(id, productId);
    }

    @DeleteMapping("/{id}/products/{productId}")
    public CollectionDto deleteProduct(@PathVariable("id") UUID id, @PathVariable ("productId") UUID productId) {
        return collectionService.deleteProduct(id, productId);
    }

    @PostMapping("/{id}/favorite")
    public CollectionDto setFavorite(@PathVariable("id") UUID id) {
        java.util.UUID userId = currentUserService.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated or not found");
        return collectionService.setFavorite(id, userId);
    }
}
