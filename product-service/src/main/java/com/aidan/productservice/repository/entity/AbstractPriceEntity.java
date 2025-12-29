package com.aidan.productservice.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "price")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "price_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "avg_price")
    private BigDecimal avgPrice;

    @Column(name = "normal_price")
    private BigDecimal normalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", insertable = false, updatable = false)
    private PriceSourceType priceType;

    protected AbstractPriceEntity(BigDecimal avgPrice, BigDecimal normalPrice, PriceSourceType priceType) {
        this.avgPrice = avgPrice;
        this.normalPrice = normalPrice;
        this.priceType = priceType;
    }
}

