
package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks

    private SearchService searchService;

    private Product createTestProduct(String product) {
        return new SimpleProduct(product, 111, UUID.randomUUID());
    }

    private Article createTestArticle(String article) {
        return new Article(article, "Test", UUID.randomUUID());
    }

    @Test
    public void NoObjects_Search_ReturnEmptyList() {

        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("виноград");

        assertTrue(results.isEmpty());

        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    public void NoMatchingObjects_search_ReturnsEmptyList() {

        Product product = createTestProduct("чай");
        Article article = createTestArticle("печенье");
        when(storageService.getAllSearchables())
                .thenReturn(Arrays.asList(product, article));

        Collection<SearchResult> results = searchService.search("виноград");

        assertTrue(results.isEmpty());

        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    public void OneMatchingProduct_search_ReturnsOneResult() {
        Product product1 = createTestProduct("чай");
        Product product2 = createTestProduct("печенье");
        when(storageService.getAllSearchables())
                .thenReturn(Arrays.asList(product1, product2));

        Collection<SearchResult> results = searchService.search("чай");

        assertEquals(1, results.size());

        SearchResult result = results.iterator().next();

        assertEquals(product1.getSearchableName(), result.getName());

        verify(storageService, times(1)).getAllSearchables();
    }

}