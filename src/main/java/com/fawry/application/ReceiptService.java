package com.fawry.application;

import com.fawry.domain.model.cart.CartItem;
import java.io.PrintStream;

public class ReceiptService {
    private final PrintStream output;

    public ReceiptService() {
        this(System.out);
    }

    public ReceiptService(PrintStream output) {
        this.output = output;
    }

    public void printReceipt(CheckoutResult result) {
        output.println("** Checkout receipt **");

        for (CartItem item : result.purchasedItems()) {
            output.printf("%dx %-14s %8.0f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.calculateSubtotal().getAmount().doubleValue());
        }

        // totals
        output.println("----------------------");
        output.printf("Subtotal         %8.0f%n",
                result.subtotal().getAmount().doubleValue());
        output.printf("Shipping         %8.0f%n",
                result.shippingCost().getAmount().doubleValue());
        output.printf("Amount           %8.0f%n",
                result.total().getAmount().doubleValue());
        output.printf("Remaining balance: $%.2f%n",
                result.remainingBalance().getAmount().doubleValue());
        output.println();
    }

    public void printSummary(CheckoutResult result) {
        output.printf("Checkout completed: %d items, Total: %s%n",
                result.getTotalItemCount(),
                result.total());

        if (result.hasShipping()) {
            output.printf("Shipping required: %s%n", result.shippingCost());
        }
    }
}
