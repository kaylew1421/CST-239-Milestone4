package com.ck.store;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> lines = new LinkedHashMap<>();

    public void add(Product p, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        lines.merge(p, qty, Integer::sum);
    }
    public void remove(Product p, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        lines.computeIfPresent(p, (k, v) -> {
            int left = v - qty;
            return left > 0 ? left : null;
        });
    }
    public double total() {
        return lines.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }
    public Map<Product, Integer> getLines() { return lines; }
}
