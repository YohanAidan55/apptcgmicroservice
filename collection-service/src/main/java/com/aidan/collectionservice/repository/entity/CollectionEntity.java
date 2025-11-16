package com.aidan.collectionservice.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collection")
@Getter
@Setter
@NoArgsConstructor
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollectionProductEntity> collectionProducts = new ArrayList<>();

    @Column(name = "total_products")
    private Integer totalProducts = 0;
}
