package com.fawry.domain.service;

import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.cart.ShippableItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShippingService {
    private static final Money SHIPPING_RATE_PER_KG = new Money(10.0);
    private static final Money FREE_SHIPPING_THRESHOLD = new Money(16000.0);

    public Money calculateShippingCost(List<ShippableItem> items) {
        if (items == null || items.isEmpty()) {
            return Money.ZERO;
        }

        double totalWeight = items.stream()
                .mapToDouble(item -> item.getKilograms())
                .sum();

        return SHIPPING_RATE_PER_KG.multiply(totalWeight);
    }

    public Money calculateShippingCostWithDiscounts(List<ShippableItem> items, Money orderSubtotal) {
        // Free shipping for large orders
        if (orderSubtotal.isGreaterThan(FREE_SHIPPING_THRESHOLD) || orderSubtotal.equals(FREE_SHIPPING_THRESHOLD)) {
            return Money.ZERO;
        }

        return calculateShippingCost(items);
    }

    public void processShipment(List<ShippableItem> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        System.out.println("**   Shipment notice   **");

        Map<String, List<ShippableItem>> groupedItems = items.stream()
                .collect(Collectors.groupingBy(ShippableItem::name));

        double totalWeight = 0.0;

        for (Map.Entry<String, List<ShippableItem>> entry : groupedItems.entrySet()) {
            String itemName = entry.getKey();
            List<ShippableItem> itemList = entry.getValue();
            int quantity = itemList.size();
            double itemWeight = itemList.get(0).getKilograms();

            System.out.printf("%dx %-14s %8.0fg%n",
                    quantity, itemName, itemWeight * 1000);

            totalWeight += itemWeight * quantity;
        }

        System.out.printf("Total weight %.1fkg%n%n", totalWeight);
    }

    public Money getShippingRatePerKg() {
        return SHIPPING_RATE_PER_KG;
    }

    public Money getFreeShippingThreshold() {
        return FREE_SHIPPING_THRESHOLD;
    }
}
