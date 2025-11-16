package com.aidan.collectionservice.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CollectionDto {
    private UUID id;
    private String name;
    private Integer totalProducts;
    private List<CollectionProductDto> collectionProducts;
}
