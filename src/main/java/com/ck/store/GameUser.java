package com.ck.store;

public class GameUser {
    private final String id;
    private final String username;
    private final ShoppingCart cart = new ShoppingCart();

    public GameUser(String id, String username) {
        this.id = id;
        this.username = username;
    }
    public String getId() { return id; }
    public String getUsername() { return username; }
    public ShoppingCart getCart() { return cart; }
}
