package com.fawry.domain.model.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Weight {
    public static final Weight ZERO = new Weight(BigDecimal.ZERO);

    private final BigDecimal amount;
    private final String unit;

    public Weight(BigDecimal amount, String unit) {
        if (amount == null) {
            throw new IllegalArgumentException("Weight amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Weight amount cannot be negative");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Weight unit cannot be null or empty");
        }
        this.amount = amount;
        this.unit = unit.trim();
    }

    public Weight(double amount, String unit) {
        this(BigDecimal.valueOf(amount), unit);
    }

    public Weight(int amount, String unit) {
        this(BigDecimal.valueOf(amount), unit);
    }

    public Weight(BigDecimal amount) {
        this(amount, "kg");
    }

    public Weight(double amount) {
        this(BigDecimal.valueOf(amount), "kg");
    }

    public Weight(int amount) {
        this(BigDecimal.valueOf(amount), "kg");
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public Weight add(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Other weight cannot be null");
        }
        if (!this.unit.equals(other.unit)) {
            throw new IllegalArgumentException("Cannot add weights with different units");
        }
        return new Weight(this.amount.add(other.amount), this.unit);
    }

    public Weight multiply(int multiplier) {
        return new Weight(this.amount.multiply(BigDecimal.valueOf(multiplier)), this.unit);
    }

    public Weight multiply(BigDecimal multiplier) {
        if (multiplier == null) {
            throw new IllegalArgumentException("Multiplier cannot be null");
        }
        return new Weight(this.amount.multiply(multiplier), this.unit);
    }

    public boolean isGreaterThan(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Other weight cannot be null");
        }
        if (!this.unit.equals(other.unit)) {
            throw new IllegalArgumentException("Cannot compare weights with different units");
        }
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public String toString() {
        return amount.toString() + " " + unit;
    }
}
