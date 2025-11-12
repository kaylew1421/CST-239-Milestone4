package com.ck.store;


public class InventoryItem {
    private Product product;
    private int quantity;

    public InventoryItem() {}
    public InventoryItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void increment(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        quantity += amount;
    }
    public void decrement(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        if (amount > quantity) throw new IllegalArgumentException("insufficient stock");
        quantity -= amount;
    }
}
