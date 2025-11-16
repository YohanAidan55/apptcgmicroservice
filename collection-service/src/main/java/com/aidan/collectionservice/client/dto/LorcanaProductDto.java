package com.aidan.collectionservice.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class LorcanaProductDto extends AbstractProductDto {
    protected ProductSubType productSubType;
}

