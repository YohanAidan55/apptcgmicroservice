package com.aidan.collectionservice.repository;

import com.aidan.collectionservice.repository.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, UUID> {
    List<CollectionEntity> findAllByUserId(UUID userId);

    Optional<CollectionEntity> findByUserIdAndFavoriteTrue(UUID userId);
}
