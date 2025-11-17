package com.aidan.productservice.service;

import com.aidan.productservice.repository.ExpansionRepository;
import com.aidan.productservice.repository.ProductRepository;
import com.aidan.productservice.repository.entity.AbstractProductEntity;
import com.aidan.productservice.repository.entity.CardMarketMetadata;
import com.aidan.productservice.repository.entity.ExpansionEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductExpansionSyncService {

    private final ProductRepository productRepository;
    private final ExpansionRepository expansionRepository;

    /**
     * Parcourt tous les produits et renseigne product.expansion en se basant sur
     * cardMarketMetadata.expansionId -> table expansion.card_market_expansion_id
     */
    @Transactional
    public void syncExpansionNames() {
        log.info("Démarrage de la synchronisation des expansionName pour les produits");

        List<AbstractProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) {
            log.info("Aucun produit trouvé");
            return;
        }

        // Pour chaque produit qui a un cardMarketMetadata.expansionId, on recherche l'expansion
        List<AbstractProductEntity> modified = products.stream()
                .filter(Objects::nonNull)
                .map(p -> {
                    CardMarketMetadata meta = p.getCardMarketMetadata();
                    if (meta == null || meta.getExpansionId() == null) {
                        return null;
                    }

                    Integer cardMarketExpansionId = meta.getExpansionId();
                    Optional<ExpansionEntity> expansionOpt = expansionRepository.findByCardMarketExpansionId(cardMarketExpansionId);
                    if (expansionOpt.isEmpty()) {
                        // Pas d'expansion correspondante, on ne modifie pas le produit
                        return null;
                    }

                    ExpansionEntity expansion = expansionOpt.get();

                    // Si le produit n'a pas d'expansion associée ou si l'association est différente,
                    // on associe l'entité expansion complète au produit.
                    if (p.getExpansion() == null
                            || !Objects.equals(p.getExpansion().getCardMarketExpansionId(), expansion.getCardMarketExpansionId())) {
                        p.setExpansion(expansion);
                        return p;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (modified.isEmpty()) {
            log.info("Aucun produit à mettre à jour");
            return;
        }

        productRepository.saveAll(modified);
        log.info("Synchronisation terminée, produits sauvegardés: {}", modified.size());
    }
}
