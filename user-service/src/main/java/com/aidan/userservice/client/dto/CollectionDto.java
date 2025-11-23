package com.aidan.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    private UUID id;
    private String name;
    private Integer totalProducts;
    private Boolean favorite;
}

