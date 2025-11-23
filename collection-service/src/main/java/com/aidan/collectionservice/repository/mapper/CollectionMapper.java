package com.aidan.collectionservice.repository.mapper;

import com.aidan.collectionservice.controller.dto.CollectionDto;
import com.aidan.collectionservice.controller.dto.CollectionProductDto;
import com.aidan.collectionservice.repository.entity.CollectionEntity;
import com.aidan.collectionservice.repository.entity.CollectionProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CollectionMapper {

    public abstract CollectionDto toDto(CollectionEntity entity);

    public abstract CollectionProductDto toDto(CollectionProductEntity entity);

    public abstract List<CollectionDto> toDto(List<CollectionEntity> entity);

}
