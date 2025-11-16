package com.aidan.collectionservice.controller;

import com.aidan.collectionservice.controller.dto.AddProductRequest;
import com.aidan.collectionservice.controller.dto.CollectionDto;
import com.aidan.collectionservice.controller.dto.CreateCollectionRequest;
import com.aidan.collectionservice.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public CollectionDto create(@RequestBody CreateCollectionRequest req) {
        return collectionService.create(req);
    }

    @GetMapping
    public List<CollectionDto> getAll() {
        return collectionService.getAll();
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

    @PostMapping("/{id}/products")
    public CollectionDto addProduct(@PathVariable("id") UUID id, @RequestBody AddProductRequest req) {
        return collectionService.addProduct(id, req);
    }
}
