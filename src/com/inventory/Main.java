
package com.inventory;

import com.inventory.model.Product;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        InventoryManagementSystem inventory =
                new InventoryManagementSystem();

        int choice;

        do {
            System.out.println("\n=== INVENTORY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Quantity");
            System.out.println("3. View Products");
            System.out.println("4. Search Product");
            System.out.println("5. Low Stock Alerts");
            System.out.println("6. Transaction History");
            System.out.println("7. Inventory Statistics");
            System.out.println("8. Undo Last Update");
            System.out.println("9. Sort Products");
            System.out.println("10. Delete Product");
            System.out.println("11. Process Low Stock Alert");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter SKU: ");
                    String sku = scanner.nextLine();

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    Product product = new Product(
                        sku, name, category, price, quantity
                    );

                    inventory.addProduct(product);
                    break;

                case 2:
                    System.out.print("Enter SKU: ");
                    sku = scanner.nextLine();

                    System.out.print("Enter new quantity: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine();

                    inventory.updateProductQuantity(sku, quantity);
                    break;

                case 3:
                    inventory.displayProducts();
                    break;

                case 4:
                    System.out.print("Enter SKU to search: ");
                    sku = scanner.nextLine();

                    Product found = inventory.searchProduct(sku);

                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 5:
                    inventory.displayLowStockAlerts();
                    break;

                case 6:
                    System.out.print(
                        "Enter number of transactions to display: "
                    );

                    int count = scanner.nextInt();
                    scanner.nextLine();

                    inventory.displayTransactionHistory(count);
                    break;

                case 7:
                    inventory.displayInventoryStatistics();
                    break;

                case 8:
                    inventory.undoLastUpdate();
                    break;

                case 9:
                    System.out.print(
                        "Sort by (sku/price/value/name): "
                    );

                    String criteria = scanner.nextLine();

                    inventory.displayProductsSortedBy(criteria);
                    break;

                case 10:
                    System.out.print("Enter SKU to delete: ");
                    sku = scanner.nextLine();

                    inventory.deleteProduct(sku);
                    break;

                case 11:
                    inventory.processLowStockAlert();
                    break;

                case 0:
                    System.out.println("Exiting Inventory System...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        scanner.close();
    }
}
