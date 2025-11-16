package com.aidan.productservice.repository.entity.tcg.lorcana;

import com.aidan.productservice.domain.ProductSubType;
import com.aidan.productservice.repository.entity.LorcanaProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "lorcana_product_card")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("LORCANA")
public class LorcanaCardEntity extends LorcanaProductEntity {

    public LorcanaCardEntity() {
        super(ProductSubType.SINGLE);
    }
}
