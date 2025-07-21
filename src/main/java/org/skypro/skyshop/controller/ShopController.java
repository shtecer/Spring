package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.skypro.skyshop.model.service.StorageService;
import org.skypro.skyshop.model.service.BasketService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {

    private final StorageService storageService;
    private final BasketService basketService;

    public ShopController(StorageService storageService, BasketService basketService) {
        this.storageService = storageService;
        this.basketService = basketService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();

    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam("pattern") String pattern) {
        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm().contains(pattern))
                .map(SearchResult::fromSearchable)
                .toList();
    }

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return "Продукт добавлен";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}
