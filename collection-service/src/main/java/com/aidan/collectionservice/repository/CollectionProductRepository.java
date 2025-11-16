package com.aidan.collectionservice.repository;

import com.aidan.collectionservice.repository.entity.CollectionProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CollectionProductRepository extends JpaRepository<CollectionProductEntity, UUID> {
}

