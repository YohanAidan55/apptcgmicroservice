package com.aidan.productservice.repository.entity;

import com.aidan.productservice.domain.ProductFamilyType;
import com.aidan.productservice.domain.ProductSubType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "onepiece_product")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class OnePieceProductEntity extends AbstractProductEntity {

    @Enumerated(EnumType.STRING)
    protected ProductSubType productSubType;

    public OnePieceProductEntity() {
        super(ProductFamilyType.ONE_PIECE);
    }

    protected OnePieceProductEntity(ProductSubType type) {
        super(ProductFamilyType.ONE_PIECE);
        this.productSubType = type;
    }
}
