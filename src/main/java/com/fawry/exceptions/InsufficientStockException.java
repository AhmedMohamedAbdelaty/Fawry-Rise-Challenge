package com.fawry.exceptions;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String productName, int requested, int available) {
        super(String.format("Insufficient stock for %s. Requested: %d, Available: %d", productName, requested, available));
    }
}
