package com.aidan.productservice.repository.entity.tcg.lorcana;

import com.aidan.productservice.domain.ProductSubType;
import com.aidan.productservice.repository.entity.LorcanaProductEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "lorcana_product_sealed")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("LORCANA")
public class LorcanaSealedEntity extends LorcanaProductEntity {
    public LorcanaSealedEntity() {
        super(ProductSubType.SEALED);

    }
}
