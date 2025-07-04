package com.fawry.exceptions;

public class ProductExpiredException extends DomainException {
    public ProductExpiredException(String message) {
        super(message);
    }
}
