package com.fawry.domain.model.product;

import java.util.UUID;

public class ProductId {
    private final String id;

    public ProductId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public ProductId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProductId productId = (ProductId) obj;
        return id.equals(productId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
