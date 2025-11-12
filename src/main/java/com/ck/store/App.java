package com.ck.store;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        FileService fs = new FileService();
        InventoryManager manager = new InventoryManager(fs);
        try {
            manager.loadFromJson(Path.of("src/main/resources/inventory.json"));
            new StoreFront(manager).runDemo();
        } catch (Exception ex) {
            System.err.println("Startup error: " + ex.getMessage());
        }
    }
}
