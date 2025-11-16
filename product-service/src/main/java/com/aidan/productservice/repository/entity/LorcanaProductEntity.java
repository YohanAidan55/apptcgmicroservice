package com.aidan.productservice.repository.entity;

import com.aidan.productservice.domain.ProductFamilyType;
import com.aidan.productservice.domain.ProductSubType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lorcana_product")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class LorcanaProductEntity extends AbstractProductEntity {

    @Enumerated(EnumType.STRING)
    protected ProductSubType productSubType;

    protected LorcanaProductEntity(ProductSubType type) {
        super(ProductFamilyType.LORCANA);
        this.productSubType = type;
    }

    protected LorcanaProductEntity() {
        super(ProductFamilyType.LORCANA);
    }
}
