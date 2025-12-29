package com.aidan.productservice.repository.entity;

import com.aidan.productservice.repository.entity.PriceSourceType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cardmarket_price")
@DiscriminatorValue("CARDMARKET")
@Getter
@Setter
@NoArgsConstructor
public class CardMarketPriceEntity extends AbstractPriceEntity {

    @Column(name = "cardmarket_listing_price")
    private BigDecimal listingPrice;

    @Column(name = "cardmarket_sold_price")
    private BigDecimal soldPrice;

    public CardMarketPriceEntity(BigDecimal avgPrice, BigDecimal normalPrice, BigDecimal listingPrice, BigDecimal soldPrice) {
        super(avgPrice, normalPrice, PriceSourceType.CARDMARKET);
        this.listingPrice = listingPrice;
        this.soldPrice = soldPrice;
    }
}
