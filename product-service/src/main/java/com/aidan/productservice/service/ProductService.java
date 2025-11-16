package com.aidan.productservice.service;

import com.aidan.productservice.controller.dto.AbstractProductDto;
import com.aidan.productservice.controller.dto.ProductFilter;
import com.aidan.productservice.repository.mapper.ProductMapper;
import com.aidan.productservice.repository.ProductRepository;
import com.aidan.productservice.repository.LorcanaProductRepository;
import com.aidan.productservice.repository.OnePieceProductRepository;
import com.aidan.productservice.repository.specification.SpecificationBuilder;
import com.aidan.productservice.repository.entity.AbstractProductEntity;
import com.aidan.productservice.repository.entity.LorcanaProductEntity;
import com.aidan.productservice.repository.entity.OnePieceProductEntity;
import org.springframework.data.jpa.domain.Specification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final LorcanaProductRepository lorcanaProductRepository;
    private final OnePieceProductRepository onePieceProductRepository;
    private final ProductMapper productMapper;

    public List<AbstractProductDto> getAll(ProductFilter filter) {
        Specification<AbstractProductEntity> spec = SpecificationBuilder.fromFilter(filter);
        return productMapper.toDtoList(productRepository.findAll(spec));
    }

    public List<AbstractProductDto> getLorcanaAll(ProductFilter filter) {
        Specification<LorcanaProductEntity> spec = SpecificationBuilder.fromFilter(filter);
        return productMapper.toDtoList(new ArrayList<>(lorcanaProductRepository.findAll(spec)));
    }

    public List<AbstractProductDto> getOnePieceAll(ProductFilter filter) {
        Specification<OnePieceProductEntity> spec = SpecificationBuilder.fromFilter(filter);
        return productMapper.toDtoList(new ArrayList<>(onePieceProductRepository.findAll(spec)));
    }
}

