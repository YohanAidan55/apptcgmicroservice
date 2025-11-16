package com.aidan.productservice.service;

import com.aidan.productservice.repository.ProductRepository;
import com.aidan.productservice.repository.entity.AbstractProductEntity;
import com.aidan.productservice.repository.entity.CardMarketMetadata;
import com.aidan.productservice.repository.entity.tcg.lorcana.LorcanaCardEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

@Service
@AllArgsConstructor
@Slf4j
public class ProductLorcanaSingleSyncService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private final ProductRepository productRepository;

    // URL fournie par l'utilisateur
    private static final String DEFAULT_LORCANA_SINGLES_URL = "https://downloads.s3.cardmarket.com/productCatalog/productList/products_singles_19.json";

    /**
     * Méthode publique pour déclencher l'import manuellement.
     */
    public void importLorcanaSingles() {
        importLorcanaSingles(DEFAULT_LORCANA_SINGLES_URL);
    }

    /**
     * Import batch depuis l'URL donnée.
     */
    public void importLorcanaSingles(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> resp = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
                log.error("Échec HTTP {} lors de la récupération de {}", resp.statusCode(), url);
                return;
            }

            JsonNode root = MAPPER.readTree(resp.body());
            JsonNode array = findArrayNode(root);
            if (array == null || !array.isArray()) {
                log.warn("Aucun tableau de produits trouvé dans le JSON récupéré depuis {}", url);
                return;
            }

            int created = 0;
            Iterator<JsonNode> it = array.elements();
            while (it.hasNext()) {
                JsonNode node = it.next();
                Integer productId = firstInt(node, "idProduct", "id", "product_id");
                String name = firstText(node, "name", "title", "name");
                String description = firstText(node, "description", "longDescription", "desc");
                Integer categoryId = firstInt(node, "idCategory", "category_id");
                String categoryName = firstText(node, "categoryName", "category_name");
                Integer metacardId = firstInt(node, "idMetacard", "metacard_id");
                Integer expansionId = firstInt(node, "idExpansion", "expansion_id");

                if (productId != null) {
                    boolean exists = productRepository.findAll().stream()
                            .map(AbstractProductEntity::getCardMarketMetadata)
                            .anyMatch(md -> md != null && productId.equals(md.getProductId()));
                    if (exists) continue;
                }

                CardMarketMetadata meta = new CardMarketMetadata();
                meta.setProductId(productId);
                meta.setName(name);
                meta.setCategoryId(categoryId);
                meta.setCategoryName(categoryName);
                meta.setMetacardId(metacardId);
                meta.setExpansionId(expansionId);

                LorcanaCardEntity card = new LorcanaCardEntity();
                card.setProductName(name != null ? name : "");
                card.setDescription(description != null ? description : "");
                card.setCardMarketMetadata(meta);

                productRepository.save(card);
                created++;
            }

            log.info("Import terminé depuis {} : {} produits créés.", url, created);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Erreur pendant l'import depuis {}: {}", url, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erreur inattendue pendant l'import depuis {}: {}", url, e.getMessage(), e);
        }
    }

    /**
     * Tâche planifiée appelée chaque jour à 03:00 du matin.
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduledImportLorcanaSingles() {
        log.info("Démarrage de l'import planifié des Lorcana singles");
        importLorcanaSingles();
    }

    private JsonNode findArrayNode(JsonNode root) {
        if (root == null) return null;
        if (root.isArray()) return root;
        if (root.has("products") && root.get("products").isArray()) return root.get("products");
        if (root.has("items") && root.get("items").isArray()) return root.get("items");
        if (root.has("data") && root.get("data").isArray()) return root.get("data");
        for (JsonNode child : root) {
            if (child.isArray()) return child;
        }
        return null;
    }

    private Integer firstInt(JsonNode node, String... keys) {
        if (node == null) return null;
        for (String k : keys) {
            if (node.has(k) && node.get(k).canConvertToInt()) return node.get(k).asInt();
        }
        return null;
    }

    private String firstText(JsonNode node, String... keys) {
        if (node == null) return null;
        for (String k : keys) {
            if (node.has(k) && !node.get(k).isNull()) return node.get(k).asText();
        }
        return null;
    }
}
