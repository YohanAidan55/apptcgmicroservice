package com.aidan.collectionservice.service;

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

    public CollectionDto create(CreateCollectionRequest req, java.util.UUID userId) {
        CollectionEntity entity = new CollectionEntity();
        entity.setName(req.getName());
        entity.setTotalProducts(0);
        entity.setUserId(userId);

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

    public List<CollectionDto> getAll(UUID userId) {
        return collectionRepository.findAllByUserId(userId).stream()
                .map(collectionMapper::toDto)
                .map(collectionEnricherService::enrich)
                .toList();
    }

    public CollectionDto update(UUID id, CreateCollectionRequest req) {
        return collectionRepository.findById(id).map(e -> {
            e.setName(req.getName());
            // Ne pas changer userId ici pour éviter le transfert d'une collection à un autre utilisateur accidentellement
            return collectionEnricherService.enrich(collectionMapper.toDto(collectionRepository.save(e)));
        })
                .orElseThrow(() -> new RuntimeException("Collection not found"));
    }

    public void delete(UUID id) {
        CollectionEntity entity = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        if (Boolean.TRUE.equals(entity.getFavorite())) {
            throw new RuntimeException("Cannot delete favorite collection");
        }

        collectionRepository.deleteById(id);
    }

    public CollectionDto addProduct(UUID collectionId, UUID productId) {
        CollectionEntity collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        Optional<CollectionProductEntity> existing = collection.getCollectionProducts().stream()
                .filter(cp -> cp.getProductId().equals(productId)).findFirst();

        CollectionProductEntity cp;
        if (existing.isPresent()) {
            cp = existing.get();
            cp.setQuantity(cp.getQuantity() + 1);
        } else {
            cp = new CollectionProductEntity();
            cp.setCollection(collection);
            cp.setProductId(productId);
            cp.setQuantity(1);
            collection.getCollectionProducts().add(cp);
        }
        collectionProductRepository.save(cp);

        int total = collection.getCollectionProducts().stream().mapToInt(CollectionProductEntity::getQuantity).sum();
        collection.setTotalProducts(total);
        collectionRepository.save(collection);
        return collectionEnricherService.enrich(collectionMapper.toDto(collection));
    }

    public CollectionDto deleteProduct(UUID id, UUID productId) {
        CollectionEntity collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        Optional<CollectionProductEntity> existing = collection.getCollectionProducts().stream()
                .filter(cp -> cp.getProductId().equals(productId))
                .findFirst();

        if (existing.isEmpty()) {
            throw new RuntimeException("Product not found in collection");
        }

        CollectionProductEntity cp = existing.get();

        if (cp.getQuantity() > 1) {
            cp.setQuantity(cp.getQuantity() - 1);
            collectionProductRepository.save(cp);
        } else {
            collection.getCollectionProducts().remove(cp);
            collectionProductRepository.delete(cp);
        }

        int total = collection.getCollectionProducts().stream().mapToInt(CollectionProductEntity::getQuantity).sum();
        collection.setTotalProducts(total);
        collectionRepository.save(collection);

        return collectionEnricherService.enrich(collectionMapper.toDto(collection));
    }

    public CollectionDto setFavorite(UUID collectionId, java.util.UUID userId) {
        CollectionEntity toSet = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        if (!userId.equals(toSet.getUserId())) {
            throw new RuntimeException("Not allowed to change favorite for another user");
        }

        // Disable previous favorite if exists
        collectionRepository.findByUserIdAndFavoriteTrue(userId).ifPresent(prev -> {
            if (!prev.getId().equals(collectionId)) {
                prev.setFavorite(false);
                collectionRepository.save(prev);
            }
        });

        toSet.setFavorite(true);
        CollectionEntity saved = collectionRepository.save(toSet);

        return collectionEnricherService.enrich(collectionMapper.toDto(saved));
    }
}
