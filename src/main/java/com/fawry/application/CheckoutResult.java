package com.fawry.application;

import com.fawry.domain.model.cart.CartItem;
import com.fawry.domain.model.valueobject.Money;
import java.util.Collection;
import java.util.List;

public record CheckoutResult(
        Collection<CartItem> purchasedItems,
        Money subtotal,
        Money shippingCost,
        Money total,
        Money remainingBalance) {
    public CheckoutResult {
        if (subtotal == null) {
            throw new IllegalArgumentException("Subtotal cannot be null");
        }
        if (shippingCost == null) {
            throw new IllegalArgumentException("Shipping cost cannot be null");
        }
        if (total == null) {
            throw new IllegalArgumentException("Total cannot be null");
        }
        if (remainingBalance == null) {
            throw new IllegalArgumentException("Remaining balance cannot be null");
        }
        if (purchasedItems != null) {
            purchasedItems = List.copyOf(purchasedItems);
        }
    }

    public boolean hasShipping() {
        return shippingCost != null && shippingCost.isGreaterThan(Money.ZERO);
    }

    public int getTotalItemCount() {
        return purchasedItems == null ? 0
                : purchasedItems.stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum();
    }
}
