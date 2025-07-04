package com.fawry.domain.model.product;

import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;

public interface Shippable {
    Weight getWeight();

    Money calculateShippingCost();

    default boolean requiresSpecialHandling() {
        return false;
    }
}
