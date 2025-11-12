package com.ck.store;

import java.util.Scanner;

public class StoreFront {
    private final InventoryManager inventory;

    public StoreFront(InventoryManager inventory) {
        this.inventory = inventory;
    }

    public void runDemo() {
        GameUser user = new GameUser("u1", "PlayerOne");
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Game Store ===");

        loop: while (true) {
            System.out.println("\n1) List  2) Add  3) Checkout  4) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    inventory.allItems().forEach(ii ->
                        System.out.printf("%s (%s) $%.2f | qty=%d\n",
                            ii.getProduct().getId(),
                            ii.getProduct().getName(),
                            ii.getProduct().getPrice(),
                            ii.getQuantity()));
                    break;
                case "2":
                    try {
                        System.out.print("Product id: ");
                        String pid = sc.nextLine().trim();
                        System.out.print("Qty: ");
                        int q = Integer.parseInt(sc.nextLine().trim());
                        if (!inventory.inStock(pid, q)) {
                            System.out.println("Not enough stock.");
                            break;
                        }
                        var ii = inventory.getByProductId(pid);
                        user.getCart().add(ii.getProduct(), q);
                        System.out.println("Added.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "3":
                    double total = user.getCart().total();
                    if (total == 0) { System.out.println("Cart empty."); break; }
                    user.getCart().getLines().forEach((p, q) -> {
                        try { inventory.decrementStock(p.getId(), q); }
                        catch (InventoryException e) { System.out.println("Checkout error: " + e.getMessage()); }
                    });
                    System.out.printf("Checked out! Total $%.2f\n", total);
                    user.getCart().getLines().clear();
                    break;
                case "4":
                    break loop;
                default:
                    System.out.println("Invalid.");
            }
        }
        System.out.println("Goodbye.");
    }
}
