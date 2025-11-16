package com.aidan.productservice.controller.dto;

import com.aidan.productservice.domain.ProductFamilyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractProductDto {
    private UUID id;
    private String productName;
    private String description;
    private ProductFamilyType productFamilyType;
    private CardMarketMetadataDto cardMarketMetadata;
    private String expansionName;
}
