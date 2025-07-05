package com.fawry.domain.model.product;

import com.fawry.domain.exception.ProductExpiredException;
import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Should create standard product successfully")
    void shouldCreateStandardProduct() {
        String name = "Mobile Scratch Card";
        Money price = new Money(50);
        int quantity = 100;

        StandardProduct product = new StandardProduct(name, price, quantity);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertNotNull(product.getProductId());
    }

    @Test
    @DisplayName("Should create expirable product successfully")
    void shouldCreateExpirableProduct() {
        String name = "Milk";
        Money price = new Money(25);
        int quantity = 10;
        LocalDate expirationDate = LocalDate.now().plusDays(7);

        ExpirableProduct product = new ExpirableProduct(name, price, quantity, expirationDate);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertEquals(expirationDate, product.getExpirationDate());
        assertFalse(product.isExpired());
    }

    @Test
    @DisplayName("Should throw exception when creating product with null name")
    void shouldThrowExceptionWhenNameIsNull() {
        Money price = new Money(50);
        int quantity = 100;

        assertThrows(IllegalArgumentException.class, () -> {
            new StandardProduct(null, price, quantity);
        });
    }

    @Test
    @DisplayName("Should throw exception when creating product with negative quantity")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        String name = "Test Product";
        Money price = new Money(50);
        int quantity = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            new StandardProduct(name, price, quantity);
        });
    }

    @Test
    @DisplayName("Should reduce quantity correctly")
    void shouldReduceQuantityCorrectly() {
        StandardProduct product = new StandardProduct("Test", new Money(10), 100);

        product.reduceQuantity(30);

        assertEquals(70, product.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when reducing quantity below available")
    void shouldThrowExceptionWhenReducingQuantityBelowAvailable() {
        StandardProduct product = new StandardProduct("Test", new Money(10), 5);

        assertThrows(Exception.class, () -> {
            product.reduceQuantity(10);
        });
    }

    @Test
    @DisplayName("Should validate expired product correctly")
    void shouldValidateExpiredProduct() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        ExpirableProduct product = new ExpirableProduct("Expired Milk", new Money(20), 5, pastDate);

        assertThrows(ProductExpiredException.class, () -> {
            product.validateForPurchase();
        });
    }

    @Test
    @DisplayName("Should calculate subtotal correctly")
    void shouldCalculateSubtotalCorrectly() {
        StandardProduct product = new StandardProduct("Test", new Money(25), 100);

        Money subtotal = product.calculateSubtotal(4);

        assertEquals(new Money(100), subtotal);
    }

    @Test
    @DisplayName("Should create shippable product with weight")
    void shouldCreateShippableProductWithWeight() {
        String name = "Laptop";
        Money price = new Money(1500);
        int quantity = 5;
        Weight weight = new Weight(2.5);

        ShippableProduct product = new ShippableProduct(name, price, quantity, weight);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertEquals(weight, product.getWeight());
    }

    @Test
    @DisplayName("Should create expirable and shippable product")
    void shouldCreateExpirableAndShippableProduct() {
        String name = "Cheese";
        Money price = new Money(45);
        int quantity = 20;
        Weight weight = new Weight(0.5);
        LocalDate expirationDate = LocalDate.now().plusDays(14);

        ExpirableShippableProduct product = new ExpirableShippableProduct(
                name, price, quantity, weight, expirationDate);

        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertEquals(weight, product.getWeight());
        assertEquals(expirationDate, product.getExpirationDate());
        assertFalse(product.isExpired());
    }
}
