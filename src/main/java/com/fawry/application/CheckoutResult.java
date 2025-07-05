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
