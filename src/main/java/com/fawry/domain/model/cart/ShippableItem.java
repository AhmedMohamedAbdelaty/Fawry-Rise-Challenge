package com.fawry.domain.model.cart;

import com.fawry.domain.model.valueobject.Weight;

public record ShippableItem(String name, Weight weight) {

    public ShippableItem {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Shippable item name cannot be null or empty");
        }
        if (weight == null) {
            throw new IllegalArgumentException("Shippable item weight cannot be null");
        }
    }

    public double getKilograms() {
        return weight.getAmount().doubleValue();
    }
}
