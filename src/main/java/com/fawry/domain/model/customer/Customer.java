package com.fawry.domain.model.customer;

import com.fawry.domain.exception.InsufficientStockException;
import com.fawry.domain.model.cart.Cart;
import com.fawry.domain.model.product.Product;
import com.fawry.domain.model.product.ProductId;
import com.fawry.domain.model.valueobject.Money;

public class Customer {
    private final String name;
    private Money walletBalance;
    private final Cart cart;

    public Customer(String name, Money walletBalance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (walletBalance == null) {
            throw new IllegalArgumentException("Wallet balance cannot be null");
        }

        this.name = name.trim();
        this.walletBalance = walletBalance;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public Money getWalletBalance() {
        return walletBalance;
    }

    public Cart getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!product.isAvailable(quantity)) {
            throw new InsufficientStockException(
                    "Insufficient stock for " + product.getName() +
                            ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        try {
            cart.addItem(product, quantity);
            System.out.println("Added " + quantity + "x " + product.getName() + " to cart");
        } catch (Exception e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
        }

    }

    public void removeFromCart(ProductId productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        cart.removeItem(productId);
    }

    public void updateCartItemQuantity(ProductId productId, int newQuantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        cart.updateItemQuantity(productId, newQuantity);
    }

    public void deductFromWallet(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (walletBalance.isLessThan(amount)) {
            throw new IllegalArgumentException("Insufficient wallet balance");
        }
        this.walletBalance = walletBalance.subtract(amount);
    }

    public void addToWallet(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.walletBalance = walletBalance.add(amount);
    }

    public boolean canAfford(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        return walletBalance.isGreaterThanOrEqual(amount);
    }

    public Money getBalance() {
        return walletBalance;
    }

    public boolean isProductAvailable(Product product, int quantity) {
        if (product == null) {
            return false;
        }
        return product.isAvailable(quantity);
    }

    @Override
    public String toString() {
        return String.format("Customer: %s (Balance: %s)", name, walletBalance);
    }
}
