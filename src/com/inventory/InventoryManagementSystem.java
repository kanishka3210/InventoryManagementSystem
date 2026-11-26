
package com.inventory;

import com.inventory.model.Product;
import com.inventory.comparators.PriceComparator;
import com.inventory.comparators.ValueComparator;
import com.inventory.comparators.NameComparator;

import java.util.*;

public class InventoryManagementSystem {

    private HashSet<Product> productSet;
    private TreeSet<Product> sortedProducts;
    private LinkedList<String> transactionHistory;
    private Stack<Product> undoStack;
    private Queue<Product> lowStockQueue;

    public InventoryManagementSystem() {
        productSet = new HashSet<>();
        sortedProducts = new TreeSet<>();
        transactionHistory = new LinkedList<>();
        undoStack = new Stack<>();
        lowStockQueue = new LinkedList<>();
    }

    // Add a new product
    public void addProduct(Product product) {

        if (product.getPrice() < 0 || product.getQuantity() < 0) {
            System.out.println("Price and quantity cannot be negative!");
            return;
        }

        if (productSet.add(product)) {

            sortedProducts.add(product);

            transactionHistory.addFirst(
                "ADD: " + product.getSku()
            );

            if (product.getQuantity() < 10) {
                lowStockQueue.add(product);
            }

            System.out.println("Product added successfully!");

        } else {
            System.out.println(
                "Product with SKU " + product.getSku()
                + " already exists!"
            );
        }
    }

    // Search product by SKU
    public Product searchProduct(String sku) {

        for (Product product : productSet) {
            if (product.getSku().equalsIgnoreCase(sku)) {
                return product;
            }
        }

        return null;
    }

    // Display all products sorted by SKU
    public void displayProducts() {

        if (sortedProducts.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        for (Product product : sortedProducts) {
            System.out.println(product);
        }
    }

    // Display products sorted by selected criteria
    public void displayProductsSortedBy(String criteria) {

        List<Product> productList = new ArrayList<>(productSet);

        switch (criteria.toLowerCase()) {

            case "sku":
                Collections.sort(productList);
                break;

            case "price":
                Collections.sort(productList, new PriceComparator());
                break;

            case "value":
                Collections.sort(productList, new ValueComparator());
                break;

            case "name":
                Collections.sort(productList, new NameComparator());
                break;

            default:
                System.out.println("Invalid sorting criteria!");
                return;
        }

        System.out.println("\n=== PRODUCTS SORTED BY "
                + criteria.toUpperCase() + " ===");

        displayCollection(productList);
    }

    // Update product quantity
    public void updateProductQuantity(String sku, int newQuantity) {

        Product product = searchProduct(sku);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative!");
            return;
        }

        // Save previous state for undo
        Product previousState = new Product(
            product.getSku(),
            product.getName(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity()
        );

        undoStack.push(previousState);

        int oldQuantity = product.getQuantity();

        product.setQuantity(newQuantity);

        // Update low-stock queue
        if (newQuantity < 10) {
            if (!lowStockQueue.contains(product)) {
                lowStockQueue.add(product);
            }
        } else {
            lowStockQueue.remove(product);
        }

        transactionHistory.addFirst(
            "UPDATE: " + sku + " quantity changed from "
            + oldQuantity + " to " + newQuantity
        );

        System.out.println("Quantity updated successfully!");
    }

    // Undo the last quantity update
    public void undoLastUpdate() {

        if (undoStack.isEmpty()) {
            System.out.println("No operations to undo!");
            return;
        }

        Product previousState = undoStack.pop();

        Product product = searchProduct(previousState.getSku());

        if (product != null) {

            int currentQuantity = product.getQuantity();

            product.setQuantity(previousState.getQuantity());

            if (product.getQuantity() < 10) {
                if (!lowStockQueue.contains(product)) {
                    lowStockQueue.add(product);
                }
            } else {
                lowStockQueue.remove(product);
            }

            transactionHistory.addFirst(
                "UNDO: Restored quantity of " + product.getSku()
            );

            System.out.println("Last update undone!");
        }
    }

    // Display low-stock products
    public void displayLowStockAlerts() {

        System.out.println("\n=== LOW STOCK ALERTS ===");

        if (lowStockQueue.isEmpty()) {
            System.out.println("No low-stock products!");
            return;
        }

        for (Product product : lowStockQueue) {
            System.out.println(product);
        }
    }

    // Process the next low-stock alert
    public void processLowStockAlert() {

        Product product = lowStockQueue.poll();

        if (product == null) {
            System.out.println("No pending low-stock alerts!");
        } else {
            System.out.println(
                "Processed alert for: " + product.getName()
            );
        }
    }

    // Display transaction history
    public void displayTransactionHistory(int count) {

        System.out.println("\n=== TRANSACTION HISTORY ===");

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions recorded!");
            return;
        }

        int displayed = 0;

        for (String transaction : transactionHistory) {

            if (displayed >= count) {
                break;
            }

            System.out.println(transaction);
            displayed++;
        }
    }

    // Display inventory statistics
    public void displayInventoryStatistics() {

        double totalValue = 0;
        Map<String, Double> categoryValues = new HashMap<>();
        Map<String, Integer> categoryCounts = new HashMap<>();

        for (Product product : productSet) {

            double value = product.getInventoryValue();
            String category = product.getCategory();

            totalValue += value;

            categoryValues.put(
                category,
                categoryValues.getOrDefault(category, 0.0) + value
            );

            categoryCounts.put(
                category,
                categoryCounts.getOrDefault(category, 0) + 1
            );
        }

        System.out.println("\n=== INVENTORY STATISTICS ===");
        System.out.println("Total Products: " + productSet.size());

        System.out.printf(
            "Total Inventory Value: ₹%.2f%n",
            totalValue
        );

        System.out.println("\nCategory-wise Breakdown:");

        for (String category : categoryValues.keySet()) {

            double value = categoryValues.get(category);
            int count = categoryCounts.get(category);

            double percentage = totalValue == 0
                ? 0
                : (value / totalValue) * 100;

            System.out.printf(
                "%s: %d products, Value: ₹%.2f (%.1f%%)%n",
                category, count, value, percentage
            );
        }
    }

    // Delete a product
    public void deleteProduct(String sku) {

        Product product = searchProduct(sku);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        productSet.remove(product);
        sortedProducts.remove(product);
        lowStockQueue.remove(product);

        transactionHistory.addFirst(
            "DELETE: " + product.getSku()
        );

        System.out.println("Product deleted successfully!");
    }

    // Generic method to display any collection
    public static <T> void displayCollection(
            Collection<T> collection) {

        for (T item : collection) {
            System.out.println(item);
        }
    }
}
