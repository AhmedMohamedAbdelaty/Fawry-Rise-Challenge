package com.fawry.domain.model.product;

import com.fawry.domain.exception.ProductExpiredException;
import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;
import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Expirable, Shippable {
    private final LocalDate expirationDate;
    private final Weight weight;

    public ExpirableShippableProduct(String name, Money price, int quantity, Weight weight, LocalDate expirationDate) {
        super(name, price, quantity);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null for expirable product");
        }
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null for shippable product");
        }
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    public ExpirableShippableProduct(ProductId productId, String name, Money price, int quantity, Weight weight,
            LocalDate expirationDate) {
        super(productId, name, price, quantity);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null for expirable product");
        }
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null for shippable product");
        }
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired(LocalDate checkDate) {
        if (checkDate == null) {
            throw new IllegalArgumentException("Check date cannot be null");
        }
        return checkDate.isAfter(expirationDate);
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public void validateForPurchase() {
        super.validateForPurchase();
        if (isExpired()) {
            throw new ProductExpiredException("Product '" + name + "' expired on " + expirationDate);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Expirable & Shippable Product: %s [ID: %s, Price: %s, Quantity: %d, Weight: %s, Expires: %s]",
                name, productId, price, quantity, weight, expirationDate);
    }
}
