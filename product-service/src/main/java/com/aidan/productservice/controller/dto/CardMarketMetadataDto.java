package com.aidan.productservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardMarketMetadataDto {
    private UUID id;
    private Integer productId;
    private String name;
    private Integer categoryId;
    private String categoryName;
    private Integer metacardId;
    private Integer expansionId;
}

