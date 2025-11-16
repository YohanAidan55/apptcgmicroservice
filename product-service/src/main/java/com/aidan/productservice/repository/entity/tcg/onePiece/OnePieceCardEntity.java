package com.aidan.productservice.repository.entity.tcg.onePiece;

import com.aidan.productservice.domain.ProductSubType;
import com.aidan.productservice.repository.entity.OnePieceProductEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "onepiece_product_card")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("ONE_PIECE")
public class OnePieceCardEntity extends OnePieceProductEntity {
    public OnePieceCardEntity() {
        super(ProductSubType.SINGLE);

    }
}
