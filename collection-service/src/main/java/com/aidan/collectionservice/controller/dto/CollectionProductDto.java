package com.aidan.collectionservice.controller.dto;

import com.aidan.collectionservice.client.dto.AbstractProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CollectionProductDto {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    // Champ optionnel pour contenir les détails du produit récupérés depuis ProductService
    private AbstractProductDto product;
}
