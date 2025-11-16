package com.aidan.collectionservice.service;

import com.aidan.collectionservice.controller.dto.CollectionDto;
import com.aidan.collectionservice.controller.dto.CollectionProductDto;
import com.aidan.collectionservice.client.ProductClient;
import com.aidan.collectionservice.client.dto.AbstractProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CollectionEnricherService {

    private final ProductClient productClient;

    public CollectionDto enrich(CollectionDto dto) {
        if (dto == null || dto.getCollectionProducts() == null) {
            return dto;
        }

        for (CollectionProductDto cp : dto.getCollectionProducts()) {
            UUID productId = cp.getProductId();
            if (productId == null) continue;

            try {
                AbstractProductDto product = productClient.get(productId);
                    cp.setProduct(product);
            } catch (Exception e) {
                // Ne pas échouer l'enrichissement si le service produit est indisponible.
                // On laisse `product` à null.
            }
        }

        return dto;
    }

    public List<CollectionDto> enrich(List<CollectionDto> dtos) {
        if (dtos == null) return null;
        for (CollectionDto dto : dtos) enrich(dto);
        return dtos;
    }
}
