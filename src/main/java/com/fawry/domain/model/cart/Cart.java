package com.fawry.domain.model.cart;

import com.fawry.domain.exception.CartEmptyException;
import com.fawry.domain.exception.InsufficientStockException;
import com.fawry.domain.exception.InvalidProductException;
import com.fawry.domain.model.product.Product;
import com.fawry.domain.model.product.ProductId;
import com.fawry.domain.model.product.Shippable;
import com.fawry.domain.model.valueobject.Money;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cart {
    private final Map<ProductId, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        ProductId productId = product.getProductId();
        if (items.containsKey(productId)) {
            CartItem existingItem = items.get(productId);
            existingItem.increaseQuantity(quantity);
        } else {
            items.put(productId, new CartItem(product, quantity));
        }
    }

    public void removeItem(ProductId productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        CartItem removed = items.remove(productId);
        if (removed == null) {
            throw new InvalidProductException("Product not found in cart");
        }
    }

    public void updateItemQuantity(ProductId productId, int newQuantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        CartItem item = items.get(productId);
        if (item == null) {
            throw new InvalidProductException("Product not found in cart");
        }

        if (!item.getProduct().isAvailable(newQuantity)) {
            throw new InsufficientStockException(
                "Insufficient stock for " + item.getProduct().getName() +
                ". Available: " + item.getProduct().getQuantity() + ", Requested: " + newQuantity);
        }

        item.setQuantity(newQuantity);
    }

    public Money calculateSubtotal() {
        return items.values().stream()
                   .map(CartItem::calculateSubtotal)
                   .reduce(Money.ZERO, Money::add);
    }

    public List<ShippableItem> getShippableItems() {
        return items.values().stream()
                   .filter(item -> item.getProduct() instanceof Shippable)
                   .flatMap(this::expandShippableItem)
                   .collect(Collectors.toList());
    }

    public void validateForCheckout() {
        if (isEmpty()) {
            throw new CartEmptyException();
        }

        items.values().forEach(item -> item.getProduct().validateForPurchase());
    }

    public int getTotalItemCount() {
        return items.values().stream()
                   .mapToInt(CartItem::getQuantity)
                   .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public CartItem getItem(ProductId productId) {
        return items.get(productId);
    }

    public boolean containsProduct(ProductId productId) {
        return items.containsKey(productId);
    }

    private Stream<ShippableItem> expandShippableItem(CartItem item) {
        if (!(item.getProduct() instanceof Shippable shippable)) {
            return Stream.empty();
        }

        return Stream.generate(() ->
            new ShippableItem(item.getProduct().getName(), shippable.getWeight()))
            .limit(item.getQuantity());
    }

    @Override
    public String toString() {
        return String.format("Cart{itemCount=%d, subtotal=%s}",
                           items.size(), calculateSubtotal());
    }
}
