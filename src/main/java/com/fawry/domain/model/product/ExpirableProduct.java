package com.fawry.domain.model.product;

import com.fawry.domain.exception.ProductExpiredException;
import com.fawry.domain.model.valueobject.Money;
import java.time.LocalDate;

public class ExpirableProduct extends Product implements Expirable {
    private final LocalDate expirationDate;

    public ExpirableProduct(String name, Money price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null for expirable product");
        }
        this.expirationDate = expirationDate;
    }

    public ExpirableProduct(ProductId productId, String name, Money price, int quantity, LocalDate expirationDate) {
        super(productId, name, price, quantity);
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null for expirable product");
        }
        this.expirationDate = expirationDate;
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
    public void validateForPurchase() {
        super.validateForPurchase();
        if (isExpired()) {
            throw new ProductExpiredException("Product '" + name + "' expired on " + expirationDate);
        }
    }

    @Override
    public String toString() {
        return String.format("Expirable Product: %s [ID: %s, Price: %s, Quantity: %d, Expires: %s]",
                name, productId, price, quantity, expirationDate);
    }
}
