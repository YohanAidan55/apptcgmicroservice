package com.aidan.collectionservice.service;

import com.aidan.collectionservice.client.ProductClient;
import com.aidan.collectionservice.controller.dto.AddProductRequest;
import com.aidan.collectionservice.controller.dto.CollectionDto;
import com.aidan.collectionservice.controller.dto.CreateCollectionRequest;
import com.aidan.collectionservice.repository.CollectionProductRepository;
import com.aidan.collectionservice.repository.CollectionRepository;
import com.aidan.collectionservice.repository.entity.CollectionEntity;
import com.aidan.collectionservice.repository.entity.CollectionProductEntity;
import com.aidan.collectionservice.repository.mapper.CollectionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionProductRepository collectionProductRepository;
    private final CollectionMapper collectionMapper;
    private final CollectionEnricherService collectionEnricherService;

    public CollectionDto create(CreateCollectionRequest req) {
        CollectionEntity entity = new CollectionEntity();
        entity.setName(req.getName());
        entity.setTotalProducts(0);
        CollectionEntity saved = collectionRepository.save(entity);
        return collectionEnricherService.enrich(collectionMapper.toDto(saved));
    }

    public CollectionDto get(UUID id) {
        return collectionRepository.findById(id)
                .map(collectionMapper::toDto)
                .map(collectionEnricherService::enrich)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

    }

    public List<CollectionDto> getAll() {
        return collectionRepository.findAll().stream()
                .map(collectionMapper::toDto)
                .map(collectionEnricherService::enrich)
                .toList();
    }

    public CollectionDto update(UUID id, CreateCollectionRequest req) {
        return collectionRepository.findById(id).map(e -> {
            e.setName(req.getName());
            return collectionEnricherService.enrich(collectionMapper.toDto(collectionRepository.save(e)));
        })
                .orElseThrow(() -> new RuntimeException("Collection not found"));
    }

    public void delete(UUID id) {
        collectionRepository.deleteById(id);
    }

    public CollectionDto addProduct(UUID collectionId, AddProductRequest req) {
        CollectionEntity collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        Optional<CollectionProductEntity> existing = collection.getCollectionProducts().stream()
                .filter(cp -> cp.getProductId().equals(req.getProductId())).findFirst();

        CollectionProductEntity cp;
        if (existing.isPresent()) {
            cp = existing.get();
            cp.setQuantity(cp.getQuantity() + 1);
        } else {
            cp = new CollectionProductEntity();
            cp.setCollection(collection);
            cp.setProductId(req.getProductId());
            cp.setQuantity(1);
            collection.getCollectionProducts().add(cp);
        }
        collectionProductRepository.save(cp);

        int total = collection.getCollectionProducts().stream().mapToInt(CollectionProductEntity::getQuantity).sum();
        collection.setTotalProducts(total);
        collectionRepository.save(collection);
        return collectionEnricherService.enrich(collectionMapper.toDto(collection));
    }

}
