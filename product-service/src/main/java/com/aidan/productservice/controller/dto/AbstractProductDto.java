package com.aidan.productservice.controller.dto;

import com.aidan.productservice.repository.entity.ExpansionEntity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.aidan.productservice.domain.ProductFamilyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = com.aidan.productservice.controller.dto.tcg.lorcana.LorcanaCardDto.class, name = "lorcana-card"),
        @JsonSubTypes.Type(value = com.aidan.productservice.controller.dto.tcg.lorcana.LorcanaSealedDto.class, name = "lorcana-sealed"),
        @JsonSubTypes.Type(value = com.aidan.productservice.controller.dto.tcg.onepiece.OnePieceCardDto.class, name = "onepiece-card"),
        @JsonSubTypes.Type(value = com.aidan.productservice.controller.dto.tcg.onepiece.OnePieceSealedDto.class, name = "onepiece-sealed")
})
public abstract class AbstractProductDto {
    private UUID id;
    private String productName;
    private String description;
    private ProductFamilyType productFamilyType;
    private CardMarketMetadataDto cardMarketMetadata;
    private ExpansionEntity expansion;
    private String imageUrl;
}
