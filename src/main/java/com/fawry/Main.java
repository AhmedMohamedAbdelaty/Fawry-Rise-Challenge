package com.fawry;

import com.fawry.application.*;
import com.fawry.domain.model.customer.Customer;
import com.fawry.domain.model.product.Product;
import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.model.valueobject.Weight;
import com.fawry.domain.service.ShippingService;
import java.time.LocalDate;

public class Main {
    private static final CheckoutService checkoutService = new CheckoutService(new ShippingService());
    private static final ReceiptService receiptService = new ReceiptService();

    public static void main(String[] args) {
        testBasicCheckout();
        testInvalidInputs();
        testExpiredProduct();
        testInsufficientStock();
        testCartOperations();
        testInsufficientBalance();
        testMixedProducts();
    }

    private static void testBasicCheckout() {

        try {
            Product laptop = ProductFactory.createShippableProduct(
                    "Laptop", new Money(30000), 5, new Weight(5.0));
            Product bread = ProductFactory.createExpirableProduct(
                    "Bread", new Money(5), 20, LocalDate.now().plusDays(2));
            Product cheese = ProductFactory.createExpirableShippableProduct(
                    "Cheese", new Money(20), 20, new Weight(0.325), LocalDate.now().plusDays(10));

            Customer customer = new Customer("Ahmed", new Money(125000));
            customer.addToCart(laptop, 1);
            customer.addToCart(bread, 10);
            customer.addToCart(cheese, 3);

            CheckoutResult result = checkoutService.processCheckout(customer);
            receiptService.printReceipt(result);

        } catch (Exception e) {
            System.err.println("Error during basic checkout: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testInvalidInputs() {

        try {
            Customer customer = new Customer("Ahmed", new Money(1000));
            Product bread = ProductFactory.createExpirableProduct(
                    "Bread", new Money(5), 20, LocalDate.now().plusDays(2));

            System.out.println("Testing null product...");
            customer.addToCart(null, 1);

            System.out.println("Testing negative quantity...");
            customer.addToCart(bread, -5);

            System.out.println("Testing zero quantity...");
            customer.addToCart(bread, 0);

            // Try to checkout
            if (!customer.getCart().isEmpty()) {
                CheckoutResult result = checkoutService.processCheckout(customer);
                receiptService.printReceipt(result);
            } else {
                System.out.println("Cart is empty - cannot checkout");
            }

        } catch (Exception e) {
            System.err.println("Error during invalid inputs test: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testExpiredProduct() {
        try {
            Customer customer = new Customer("Ahmed", new Money(1000));
            Product expiredMilk = ProductFactory.createExpirableProduct(
                    "Milk", new Money(10), 10, LocalDate.now().minusDays(1));

            System.out.println("Attempting to add expired product...");
            customer.addToCart(expiredMilk, 1);

            // Try to checkout
            if (!customer.getCart().isEmpty()) {
                CheckoutResult result = checkoutService.processCheckout(customer);
                receiptService.printReceipt(result);
            } else {
                System.out.println("Cart is empty - cannot checkout");
            }

        } catch (Exception e) {
            System.err.println("Error during expired product test: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testInsufficientStock() {

        try {
            Customer customer = new Customer("Ahmed", new Money(100));
            Product laptop = ProductFactory.createShippableProduct(
                    "Laptop", new Money(1), 5, new Weight(1.0));

            // First purchase - succeed
            customer.addToCart(laptop, 5);
            CheckoutResult result1 = checkoutService.processCheckout(customer);
            receiptService.printReceipt(result1);

            // Second - should fail - no stock left
            System.out.println("Attempting to purchase more than available...");
            customer.addToCart(laptop, 1);

            if (!customer.getCart().isEmpty()) {
                CheckoutResult result2 = checkoutService.processCheckout(customer);
                receiptService.printReceipt(result2);
            } else {
                System.out.println("Cart is empty - cannot checkout");
            }

        } catch (Exception e) {
            System.err.println("Error during insufficient stock test: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testCartOperations() {

        try {
            Customer customer = new Customer("Ahmed", new Money(50000));
            Product laptop = ProductFactory.createShippableProduct(
                    "Laptop", new Money(30000), 5, new Weight(5.0));

            customer.addToCart(laptop, 2);

            customer.removeFromCart(laptop.getProductId());
            System.out.println("Removed laptops from cart");

            // Try empty cart
            if (!customer.getCart().isEmpty()) {
                CheckoutResult result = checkoutService.processCheckout(customer);
                receiptService.printReceipt(result);
            } else {
                System.out.println("Cart is empty - cannot checkout");
            }

        } catch (Exception e) {
            System.err.println("Error during cart operations test: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testInsufficientBalance() {

        try {
            Customer customer = new Customer("PoorCustomer", new Money(10));
            Product laptop = ProductFactory.createShippableProduct(
                    "Laptop", new Money(30000), 5, new Weight(5.0));

            customer.addToCart(laptop, 1);

            // insufficient balance
            CheckoutResult result = checkoutService.processCheckout(customer);
            receiptService.printReceipt(result);

        } catch (Exception e) {
            System.err.println("Expected error - Insufficient balance: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testMixedProducts() {

        try {
            // Create products
            Product phone = ProductFactory.builder()
                    .name("Smartphone")
                    .price(new Money(15000))
                    .quantity(3)
                    .weight(new Weight(0.2))
                    .build();

            Product yogurt = ProductFactory.builder()
                    .name("Yogurt")
                    .price(new Money(8))
                    .quantity(50)
                    .weight(new Weight(0.125))
                    .expirationDate(LocalDate.now().plusDays(7))
                    .build();

            Customer customer = new Customer("Test", new Money(50000));
            customer.addToCart(phone, 1);
            customer.addToCart(yogurt, 5);

            CheckoutResult result = checkoutService.processCheckout(customer);
            receiptService.printReceipt(result);

        } catch (Exception e) {
            System.err.println("Error during mixed products test: " + e.getMessage());
        }

        System.out.println();
    }
}
