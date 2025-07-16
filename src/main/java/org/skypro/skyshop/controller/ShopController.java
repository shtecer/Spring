package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Collection;
import java.util.List;

@RestController
public class ShopController {

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return StorageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return StorageService.getAllArticles();

    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam("pattern") String pattern) {
        return org.skypro.skyshop.model.service.StorageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm().contains(pattern))
                .map(SearchResult::fromSearchable)
                .toList();
    }
}
