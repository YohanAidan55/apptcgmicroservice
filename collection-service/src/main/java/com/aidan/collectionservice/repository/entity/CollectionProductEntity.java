package com.aidan.collectionservice.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "collection_product")
@Getter
@Setter
@NoArgsConstructor
public class CollectionProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private CollectionEntity collection;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_id", length = 36)
    private UUID productId;
}
