package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class StorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Test
    void getAllProducts_returnsNonEmptyCollection() {
        Collection<Product> products = storageService.getAllProducts();
        assertFalse(products.isEmpty());
    }

    @Test
    void getAllArticles_returnsNonEmptyCollection() {
        Collection<Article> articles = storageService.getAllArticles();
        assertFalse(articles.isEmpty());
    }

    @Test
    void getProductById_returnsProduct_ifExists() {
        UUID productId = storageService.getAllProducts().
                stream().
                findFirst().
                map(Product::getId).
                orElse(null);
        if (productId != null) {
            assertTrue(storageService.getProductById(productId).isPresent());
        }
    }

    @Test
    void getAllSearchables_returnsAllSearchables() {
        int expectedSize = storageService.getAllProducts().size() + storageService.getAllArticles().size();
        assertEquals(expectedSize, storageService.getAllSearchables().size());
    }
}