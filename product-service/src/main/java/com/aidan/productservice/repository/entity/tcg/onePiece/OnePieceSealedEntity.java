package com.aidan.productservice.repository.entity.tcg.onePiece;

import com.aidan.productservice.domain.ProductSubType;
import com.aidan.productservice.repository.entity.OnePieceProductEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "onepiece_product_sealed")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("ONE_PIECE")
public class OnePieceSealedEntity extends OnePieceProductEntity {
    public OnePieceSealedEntity() {
        super(ProductSubType.SEALED);
    }
}
