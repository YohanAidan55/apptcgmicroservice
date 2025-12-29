package com.aidan.productservice.repository.entity;

import com.aidan.productservice.domain.ProductFamilyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_family_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productName;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_family_type", insertable = false, updatable = false)
    private ProductFamilyType productFamilyType;

    @Embedded
    private CardMarketMetadata cardMarketMetadata;

    @OneToOne(fetch = FetchType.EAGER)
    private ExpansionEntity expansion;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private AbstractPriceEntity price;

    protected AbstractProductEntity(ProductFamilyType type) {
        this.productFamilyType = type;
    }
}
