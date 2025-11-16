package com.aidan.productservice.repository;

import com.aidan.productservice.repository.entity.OnePieceProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OnePieceProductRepository extends JpaRepository<OnePieceProductEntity, UUID>, JpaSpecificationExecutor<OnePieceProductEntity> {

}

