package com.fawry.domain.model.cart;

import com.fawry.domain.model.product.Product;
import com.fawry.domain.model.product.Shippable;
import com.fawry.domain.model.valueobject.Money;
import java.util.Objects;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount >= quantity) {
            throw new IllegalArgumentException("Cannot decrease quantity below 1");
        }
        this.quantity -= amount;
    }

    public Money calculateSubtotal() {
        return product.calculateSubtotal(quantity);
    }

    public Money calculateShippingCost() {
        if (product instanceof Shippable) {
            return ((Shippable) product).calculateShippingCost().multiply(quantity);
        }
        return Money.ZERO;
    }

    @Override
    public String toString() {
        return String.format("CartItem: %s (Quantity: %d, Subtotal: %s)",
                product.getName(), quantity, calculateSubtotal());
    }
}
