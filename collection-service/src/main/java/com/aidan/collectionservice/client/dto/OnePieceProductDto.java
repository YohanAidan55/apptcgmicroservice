package com.aidan.collectionservice.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class OnePieceProductDto extends AbstractProductDto {
    protected ProductSubType productSubType;
}

