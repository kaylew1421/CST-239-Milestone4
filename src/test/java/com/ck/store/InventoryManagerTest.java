package com.ck.store;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    @Test
    void cartTotals() {
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Product("x","Test","Cat", 2.5), 4);
        assertEquals(10.0, cart.total(), 0.0001);
    }
}
