package com.fawry.domain.model.product;

import java.time.LocalDate;

public interface Expirable {
    LocalDate getExpirationDate();

    boolean isExpired(LocalDate checkDate);

    default boolean isExpired() {
        return isExpired(LocalDate.now());
    }
}
