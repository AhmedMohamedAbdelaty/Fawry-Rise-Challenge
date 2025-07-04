package com.fawry.domain.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends DomainException {
    public InsufficientBalanceException(BigDecimal required, BigDecimal available) {
        super(String.format("Insufficient balance. Required: $%.2f, Available: $%.2f",
                          required, available));
    }
}
