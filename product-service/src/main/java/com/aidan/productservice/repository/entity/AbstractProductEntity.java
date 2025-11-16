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

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_family_type", insertable = false, updatable = false)
    private ProductFamilyType productFamilyType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_market_metadata_id")
    private CardMarketMetadataEntity cardMarketMetadata;

    @Column(name = "expansion_name")
    private String expansionName;

    protected AbstractProductEntity(ProductFamilyType type) {
        this.productFamilyType = type;
    }
}
