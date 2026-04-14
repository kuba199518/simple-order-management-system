package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CustomerService customerService = new CustomerService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService(customerService, productService);

        int choice;

        do {
            showMenu();
            choice = readInt(scanner, "Choose option: ");

            switch (choice) {
                case 1:
                    customerService.showCustomers();
                    break;

                case 2:
                    productService.showProducts();
                    break;

                case 3:
                    customerService.showCustomers();
                    productService.showProducts();

                    int customerId = readInt(scanner, "Enter customer ID: ");
                    int productId = readInt(scanner, "Enter product ID: ");
                    int quantity = readPositiveInt(scanner, "Enter quantity: ");

                    orderService.addOrder(customerId, productId, quantity);
                    break;

                case 4:
                    orderService.showOrders();
                    break;

                case 5:
                    orderService.showOrders();

                    int orderIdToUpdate = readInt(scanner, "Enter order ID to update: ");
                    int newQuantity = readPositiveInt(scanner, "Enter new quantity: ");

                    orderService.updateOrderQuantity(orderIdToUpdate, newQuantity);
                    break;

                case 6:
                    orderService.showOrders();

                    int orderIdToDelete = readInt(scanner, "Enter order ID to delete: ");
                    orderService.deleteOrder(orderIdToDelete);
                    break;

                case 7:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    orderService.showOrdersByCustomer(customerName);
                    break;
                case 8:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    orderService.showOrdersByDate(date);
                    break;
                case 9:
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String start = scanner.nextLine();

                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String end = scanner.nextLine();

                    orderService.showOrdersByDateRange(start, end);
                    break;

                case 0:
                    System.out.println("Exiting application...");
                    break;
                case 10:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    orderService.showTotalSpentByCustomer(name);
                    break;
                case 11:
                    orderService.showTopSellingProducts();
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== SIMPLE ORDER MANAGEMENT SYSTEM ===");
        System.out.println("1. Show customers");
        System.out.println("2. Show products");
        System.out.println("3. Add order");
        System.out.println("4. Show all orders");
        System.out.println("5. Update order quantity");
        System.out.println("6. Delete order");
        System.out.println("7. Show orders by customer");
        System.out.println("8. Show orders by date");
        System.out.println("9. Show orders by date range");
        System.out.println("10. Show total spent by customer");
        System.out.println("11. Show top selling products");
        System.out.println("0. Exit");
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            int value = readInt(scanner, message);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than 0.");
        }
    }
}