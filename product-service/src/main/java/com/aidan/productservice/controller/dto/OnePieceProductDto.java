package com.aidan.productservice.controller.dto;

import com.aidan.productservice.domain.ProductSubType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class OnePieceProductDto extends AbstractProductDto {
    protected ProductSubType productSubType;
}

