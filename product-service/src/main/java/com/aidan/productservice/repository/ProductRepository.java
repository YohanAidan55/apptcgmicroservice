package com.aidan.productservice.repository;

import com.aidan.productservice.repository.entity.AbstractProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<AbstractProductEntity, UUID>, JpaSpecificationExecutor<AbstractProductEntity> {

}
