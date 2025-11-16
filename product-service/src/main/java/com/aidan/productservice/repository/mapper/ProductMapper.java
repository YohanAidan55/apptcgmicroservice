package com.aidan.productservice.repository.mapper;

import com.aidan.productservice.controller.dto.*;
import com.aidan.productservice.controller.dto.tcg.lorcana.LorcanaCardDto;
import com.aidan.productservice.controller.dto.tcg.lorcana.LorcanaSealedDto;
import com.aidan.productservice.controller.dto.tcg.onepiece.OnePieceCardDto;
import com.aidan.productservice.controller.dto.tcg.onepiece.OnePieceSealedDto;
import com.aidan.productservice.repository.entity.*;
import com.aidan.productservice.repository.entity.tcg.lorcana.LorcanaCardEntity;
import com.aidan.productservice.repository.entity.tcg.lorcana.LorcanaSealedEntity;
import com.aidan.productservice.repository.entity.tcg.onePiece.OnePieceCardEntity;
import com.aidan.productservice.repository.entity.tcg.onePiece.OnePieceSealedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

import java.util.List;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public abstract class ProductMapper {

    public abstract CardMarketMetadataDto toDto(CardMarketMetadataEntity e);

    @SubclassMappings({
            @SubclassMapping(source = LorcanaCardEntity.class, target = LorcanaCardDto.class),
            @SubclassMapping(source = LorcanaSealedEntity.class, target = LorcanaSealedDto.class),
            @SubclassMapping(source = OnePieceCardEntity.class, target = OnePieceCardDto.class),
            @SubclassMapping(source = OnePieceSealedEntity.class, target = OnePieceSealedDto.class)
    })
    public abstract AbstractProductDto toDto(AbstractProductEntity e);

    public abstract LorcanaCardDto toDto(LorcanaCardEntity e);
    public abstract LorcanaSealedDto toDto(LorcanaSealedEntity e);
    public abstract OnePieceCardDto toDto(OnePieceCardEntity e);
    public abstract OnePieceSealedDto toDto(OnePieceSealedEntity e);

    public abstract List<AbstractProductDto> toDtoList(List<? extends AbstractProductEntity> entities);
}
