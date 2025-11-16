package com.aidan.productservice.controller;

import com.aidan.productservice.controller.dto.AbstractProductDto;
import com.aidan.productservice.controller.dto.ProductFilter;
import com.aidan.productservice.service.ProductExpansionSyncService;
import com.aidan.productservice.service.ProductLorcanaSingleSyncService;
import com.aidan.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductLorcanaSingleSyncService productLorcanaSingleSyncService;
    private final ProductExpansionSyncService productExpansionSyncService;

    @GetMapping
    List<AbstractProductDto> getAll(
            @Parameter(description = "Filtre des produits")
            @ModelAttribute ProductFilter filter) {
        return productService.getAll(filter);
    }

    @GetMapping("/{id}")
    AbstractProductDto get(@PathVariable("id") UUID id) {
        return productService.get(id);
    }

    @GetMapping("/lorcana")
    List<AbstractProductDto> getLorcana(
            @Parameter(description = "Filtre des produits Lorcana")
            @ModelAttribute ProductFilter filter) {
        return productService.getLorcanaAll(filter);
    }

    @GetMapping("/onepiece")
    List<AbstractProductDto> getOnePiece(
            @Parameter(description = "Filtre des produits One Piece")
            @ModelAttribute ProductFilter filter) {
        return productService.getOnePieceAll(filter);
    }

    @PostMapping("/syncCard")
    void syncCard() {
        productLorcanaSingleSyncService.scheduledImportLorcanaSingles();
    }

    @PostMapping("/syncExpansion")
    void syncExpansion() {
        productExpansionSyncService.syncExpansionNames();
    }
}

