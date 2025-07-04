package com.fawry.domain.model.product;

import com.fawry.domain.model.valueobject.Money;

public class StandardProduct extends Product {

    public StandardProduct(String name, Money price, int quantity) {
        super(name, price, quantity);
    }

    public StandardProduct(ProductId productId, String name, Money price, int quantity) {
        super(productId, name, price, quantity);
    }

    @Override
    public String toString() {
        return String.format("Standard Product: %s [ID: %s, Price: %s, Quantity: %d]",
                name, productId, price, quantity);
    }
}
