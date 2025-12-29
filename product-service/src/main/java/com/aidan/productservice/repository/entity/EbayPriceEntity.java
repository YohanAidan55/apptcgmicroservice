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
@Table(name = "ebay_price")
@DiscriminatorValue("EBAY")
@Getter
@Setter
@NoArgsConstructor
public class EbayPriceEntity extends AbstractPriceEntity {

    @Column(name = "ebay_buy_it_now_price")
    private BigDecimal buyItNowPrice;

    @Column(name = "ebay_current_bid")
    private BigDecimal currentBid;

    public EbayPriceEntity(BigDecimal avgPrice, BigDecimal normalPrice, BigDecimal buyItNowPrice, BigDecimal currentBid) {
        super(avgPrice, normalPrice, PriceSourceType.EBAY);
        this.buyItNowPrice = buyItNowPrice;
        this.currentBid = currentBid;
    }
}
