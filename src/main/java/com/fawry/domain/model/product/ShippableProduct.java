package com.fawry.domain.model.product;

import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;

public class ShippableProduct extends Product implements Shippable {
    private final Weight weight;

    public ShippableProduct(String name, Money price, int quantity, Weight weight) {
        super(name, price, quantity);
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null for shippable product");
        }
        this.weight = weight;
    }

    public ShippableProduct(ProductId productId, String name, Money price, int quantity, Weight weight) {
        super(productId, name, price, quantity);
        if (weight == null) {
            throw new IllegalArgumentException("Weight cannot be null for shippable product");
        }
        this.weight = weight;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("Shippable Product: %s [ID: %s, Price: %s, Quantity: %d, Weight: %s]",
                name, productId, price, quantity, weight);
    }
}
