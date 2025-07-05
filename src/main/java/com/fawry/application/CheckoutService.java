package com.fawry.application;

import com.fawry.domain.exception.CartEmptyException;
import com.fawry.domain.exception.InsufficientBalanceException;
import com.fawry.domain.model.cart.Cart;
import com.fawry.domain.model.cart.CartItem;
import com.fawry.domain.model.cart.ShippableItem;
import com.fawry.domain.model.customer.Customer;
import com.fawry.domain.model.valueobject.Money;
import com.fawry.domain.service.ShippingService;
import java.util.Collection;
import java.util.List;

public class CheckoutService {
    private final ShippingService shippingService;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public CheckoutResult processCheckout(Customer customer) {
        Cart cart = customer.getCart();

        // Validate the cart and items
        validateCheckout(cart);

        Money subtotal = cart.calculateSubtotal();
        List<ShippableItem> shippableItems = cart.getShippableItems();
        Money shippingCost = shippingService.calculateShippingCostWithDiscounts(shippableItems, subtotal);
        Money total = subtotal.add(shippingCost);

        // Check customer
        if (!customer.canAfford(total)) {
            throw new InsufficientBalanceException(total.getAmount(), customer.getBalance().getAmount());
        }

        // payment
        customer.deductFromWallet(total);

        // update inventory
        updateInventory(cart.getItems());

        // process shipping
        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }

        // clear cart and return result
        CheckoutResult result = new CheckoutResult(
                cart.getItems(),
                subtotal,
                shippingCost,
                total,
                customer.getBalance());

        cart.clear();

        return result;
    }

    private void validateCheckout(Cart cart) {
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        cart.validateForCheckout();
    }

    private void updateInventory(Collection<CartItem> items) {
        items.forEach(item -> item.getProduct().reduceQuantity(item.getQuantity()));
    }
}
