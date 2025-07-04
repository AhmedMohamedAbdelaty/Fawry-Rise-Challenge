package com.fawry.domain.model.product;

import com.fawry.domain.exception.InvalidProductException;
import com.fawry.domain.model.valueobject.Money;
public abstract class Product {
    protected final ProductId productId;
    protected final String name;
    protected final Money price;
    protected int quantity;

    protected Product(String name, Money price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }

        this.productId = new ProductId();
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    protected Product(ProductId productId, String name, Money price, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }

        this.productId = productId;
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public boolean isAvailable(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }

    public void reduceQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to reduce cannot be negative");
        }
        if (amount > quantity) {
            throw new InvalidProductException("Insufficient stock. Available: " + quantity + ", Requested: " + amount);
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase cannot be negative");
        }
        this.quantity += amount;
    }

    public Money calculateSubtotal(int requestedQuantity) {
        if (requestedQuantity < 0) {
            throw new IllegalArgumentException("Requested quantity cannot be negative");
        }
        return price.multiply(requestedQuantity);
    }

    public void validateForPurchase() {
        // Default - to be overridden
    }

    @Override
    public String toString() {
        return String.format("%s [ID: %s, Price: %s, Quantity: %d]",
                name, productId, price, quantity);
    }
}
