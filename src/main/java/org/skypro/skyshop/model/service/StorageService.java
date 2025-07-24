package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;


    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        TestData();

    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    private void TestData() {

        Product[] product = new Product[8];
        product[0] = new SimpleProduct("яблоко", 150, UUID.randomUUID());
        product[1] = new FixPriceProduct("банан", UUID.randomUUID());
        product[2] = new DiscountedProduct("лимон", 200, 15, UUID.randomUUID());
        product[3] = new DiscountedProduct("апельсин", 220, 30, UUID.randomUUID());
        product[4] = new SimpleProduct("киви", 240, UUID.randomUUID());
        product[5] = new SimpleProduct("виноград", 280, UUID.randomUUID());
        product[6] = new SimpleProduct("виноград", 300, UUID.randomUUID());
        product[7] = new SimpleProduct("виноград", 210, UUID.randomUUID());

        addProductInBasket(product[0]);
        addProductInBasket(product[1]);
        addProductInBasket(product[2]);
        addProductInBasket(product[3]);
        addProductInBasket(product[4]);
        addProductInBasket(product[5]);
        addProductInBasket(product[6]);
        addProductInBasket(product[7]);

        Article article1 = new Article("булочки", "книга рецептов", UUID.randomUUID());
        Article article2 = new Article("картофель", "советы по выращиванию", UUID.randomUUID());
        Article article3 = new Article("нарезка", "варианты сервировки", UUID.randomUUID());
        Article article4 = new Article("виноград", "варианты сервировки", UUID.randomUUID());
        Article article5 = new Article("виноград", "лучшие сорта", UUID.randomUUID());
        Article article6 = new Article("виноград", "натюрморты", UUID.randomUUID());

        addArticleInBasket(article1);
        addArticleInBasket(article2);
        addArticleInBasket(article3);
        addArticleInBasket(article4);
        addArticleInBasket(article5);
        addArticleInBasket(article6);
    }

    private void addProductInBasket(Product product) {
        products.put(product.getId(), product);
    }

    private void addArticleInBasket(Article article) {
        articles.put(article.getId(), article);
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

}