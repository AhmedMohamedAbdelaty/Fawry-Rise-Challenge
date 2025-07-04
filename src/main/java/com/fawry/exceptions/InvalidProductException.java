package com.fawry.exceptions;

public class InvalidProductException extends DomainException {
    public InvalidProductException(String message) {
        super(message);
    }
}
