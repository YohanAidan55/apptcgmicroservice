package com.aidan.productservice.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "expansion")
@Getter
@Setter
public class ExpansionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(name = "card_market_expansion_id")
    private Integer cardMarketExpansionId;
}
