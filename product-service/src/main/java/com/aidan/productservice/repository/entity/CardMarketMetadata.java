package com.aidan.productservice.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
public class CardMarketMetadata {

    @Column(name = "card_market_product_id")
    private Integer productId;

    @Column(name = "card_market_name")
    private String name;

    @Column(name = "card_market_category_id")
    private Integer categoryId;

    @Column(name = "card_market_category_name")
    private String categoryName;

    @Column(name = "card_market_metacard_id")
    private Integer metacardId;

    @Column(name = "card_market_expansion_id")
    private Integer expansionId;
}
