package com.aidan.collectionservice.client.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = com.aidan.collectionservice.client.dto.tcg.lorcana.LorcanaCardDto.class, name = "lorcana-card"),
        @JsonSubTypes.Type(value = com.aidan.collectionservice.client.dto.tcg.lorcana.LorcanaSealedDto.class, name = "lorcana-sealed"),
        @JsonSubTypes.Type(value = com.aidan.collectionservice.client.dto.tcg.onepiece.OnePieceCardDto.class, name = "onepiece-card"),
        @JsonSubTypes.Type(value = com.aidan.collectionservice.client.dto.tcg.onepiece.OnePieceSealedDto.class, name = "onepiece-sealed")
})
public abstract class AbstractProductDto {
    private UUID id;
    private String productName;
    private String description;
    private ProductFamilyType productFamilyType;
    private CardMarketMetadataDto cardMarketMetadata;
    private String expansionName;
}
