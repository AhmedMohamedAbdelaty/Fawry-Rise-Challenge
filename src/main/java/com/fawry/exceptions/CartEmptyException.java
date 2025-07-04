package com.fawry.exceptions;

public class CartEmptyException extends DomainException {
    public CartEmptyException() {
        super("Cannot checkout with an empty cart");
    }

    public CartEmptyException(String message) {
        super(message);
    }
}
