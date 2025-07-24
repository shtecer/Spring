package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.service.BasketService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private Product createTestProduct(String name, int costOfProduct){
        return new SimpleProduct(name, costOfProduct, UUID.randomUUID());
    }

    @Test
    void ProductNotExists_addToBasket_ThrowsException() {
        UUID nonExistentId = UUID.randomUUID();

        when(storageService.getProductById(nonExistentId)).thenThrow(new NoSuchProductException("nonExistentId"));

        assertThrows(NoSuchProductException.class, () ->
                basketService.addProduct(nonExistentId));

        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addToBasket_WhenProductExists_CallsAddToBasket() {
        UUID productId = UUID.randomUUID();

        Product product = createTestProduct("виноград", 111);

        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProduct(productId);

        verify(productBasket, times(1)).addProduct(productId);
    }

    @Test
    void getUserBasket_BasketEmpty_ReturnsEmptyBasket() {
        when(productBasket.getProductsBasket()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getItems().isEmpty());

        verify(storageService, never()).getProductById(any());
    }

    @Test
    void BasketHasItems_getUserBasket_ReturnsBasket() {
        Product product1 = createTestProduct("чай", 222); // Продукт 1 (будет вторым при сортировке)
        Product product2 = createTestProduct("печенье", 121); // Продукт 2 (будет первым при сортировке)

        Map<UUID, Integer> basketContents = Map.of(product1.getId(), 2, product2.getId(), 1);

        when(productBasket.getProductsBasket()).thenReturn(basketContents);

        when(storageService.getProductById(any(UUID.class))).thenAnswer(invocation -> {
            UUID productId = invocation.getArgument(0);
            return productId.equals(product1.getId()) ? Optional.of(product1) : Optional.of(product2);
        });

        UserBasket result = basketService.getUserBasket();

        assertEquals(basketContents.size(), result.getItems().size());

        int expectedTotal = basketContents.entrySet().stream()
                .mapToInt(entry -> {
                    Product p = entry.getKey().equals(product1.getId()) ? product1 : product2;
                    return p.getPrice() * entry.getValue();
                })
                .sum();
        assertEquals(expectedTotal, result.getTotalPrice());
    }
}