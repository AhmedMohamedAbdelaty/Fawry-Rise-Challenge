package com.fawry.domain.model.customer;

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
            System.err.println("Error: Cannot add null product to cart");
            return;
        }
        if (quantity <= 0) {
            System.err.println("Error: Quantity must be positive");
            return;
        }

        try {
            cart.addItem(product, quantity);
            System.out.println("Added " + quantity + " x " + product.getName() + " to cart");
        } catch (Exception e) {
            System.err.println("Error adding item to cart: " + e.getMessage());
        }
    }

    public void removeFromCart(ProductId productId) {
        if (productId == null) {
            System.err.println("Error: Cannot remove null product from cart");
            return;
        }

        try {
            cart.removeItem(productId);
            System.out.println("Removed item from cart");
        } catch (Exception e) {
            System.err.println("Error removing item from cart: " + e.getMessage());
        }
    }

    public void updateCartItemQuantity(ProductId productId, int newQuantity) {
        if (productId == null) {
            System.err.println("Error: Cannot update null product in cart");
            return;
        }
        if (newQuantity <= 0) {
            System.err.println("Error: Quantity must be positive");
            return;
        }

        try {
            cart.updateItemQuantity(productId, newQuantity);
            System.out.println("Updated item quantity in cart");
        } catch (Exception e) {
            System.err.println("Error updating item quantity: " + e.getMessage());
        }
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

    public boolean hasSufficientBalance(Money amount) {
        return canAfford(amount);
    }

    public Money getBalance() {
        return walletBalance;
    }

    public void deductBalance(Money amount) {
        deductFromWallet(amount);
    }

    @Override
    public String toString() {
        return String.format("Customer: %s (Balance: %s)", name, walletBalance);
    }
}
