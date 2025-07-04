package com.fawry.domain.exception;

public class ProductExpiredException extends DomainException {
    public ProductExpiredException(String message) {
        super(message);
    }
}
