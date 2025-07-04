package com.fawry.domain.exception;

public class CartEmptyException extends DomainException {
    public CartEmptyException() {
        super("Cannot checkout with an empty cart");
    }
}
