package com.fawry.application;

import com.fawry.domain.model.product.*;
import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;
import java.time.LocalDate;

public class ProductFactory {

    public static StandardProduct createStandardProduct(String name, Money price, int quantity) {
        return new StandardProduct(name, price, quantity);
    }

    public static ShippableProduct createShippableProduct(String name, Money price, int quantity, Weight weight) {
        return new ShippableProduct(name, price, quantity, weight);
    }

    public static ExpirableProduct createExpirableProduct(String name, Money price, int quantity, LocalDate expirationDate) {
        return new ExpirableProduct(name, price, quantity, expirationDate);
    }

    public static ExpirableShippableProduct createExpirableShippableProduct(
            String name, Money price, int quantity, Weight weight, LocalDate expirationDate) {
        return new ExpirableShippableProduct(name, price, quantity, weight, expirationDate);
    }

    public static class ProductBuilder {
        private String name;
        private Money price;
        private int quantity;
        private Weight weight;
        private LocalDate expirationDate;

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder price(Money price) {
            this.price = price;
            return this;
        }

        public ProductBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder weight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder expirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Product build() {
            validateRequiredFields();

            if (weight != null && expirationDate != null) {
                return new ExpirableShippableProduct(name, price, quantity, weight, expirationDate);
            } else if (weight != null) {
                return new ShippableProduct(name, price, quantity, weight);
            } else if (expirationDate != null) {
                return new ExpirableProduct(name, price, quantity, expirationDate);
            } else {
                return new StandardProduct(name, price, quantity);
            }
        }

        private void validateRequiredFields() {
            if (name == null) throw new IllegalArgumentException("Name is required");
            if (price == null) throw new IllegalArgumentException("Price is required");
            if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
}
