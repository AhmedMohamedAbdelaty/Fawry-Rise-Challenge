package com.fawry.domain.model.product;

import com.fawry.domain.model.valueobject.Weight;

public interface Shippable {
    Weight getWeight();

    default boolean requiresSpecialHandling() {
        return false;
    }
}
