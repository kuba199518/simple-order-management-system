package org.example;

import org.example.model.OrderSummary;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final CustomerService customerService;
    private final ProductService productService;

    public OrderService(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    public void addOrder(int customerId, int productId, int quantity) {
        if (!customerService.customerExists(customerId)) {
            System.out.println("Customer ID does not exist.");
            return;
        }

        if (!productService.productExists(productId)) {
            System.out.println("Product ID does not exist.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        String sql = """
                INSERT INTO orders (customer_id, product_id, quantity, order_date)
                VALUES (?, ?, ?, CURRENT_DATE)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Order added successfully.");
            }
        } catch (SQLException exception) {
            System.out.println("Database error while adding order: " + exception.getMessage());
        }
    }

    public List<OrderSummary> getAllOrders() {
        String sql = """
                SELECT
                    orders.id,
                    customers.name AS customer_name,
                    products.name AS product_name,
                    products.price,
                    orders.quantity,
                    orders.order_date,
                    products.price * orders.quantity AS total_value
                FROM orders
                JOIN customers ON orders.customer_id = customers.id
                JOIN products ON orders.product_id = products.id
                ORDER BY orders.id
                """;

        List<OrderSummary> orders = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                orders.add(mapOrder(resultSet));
            }
        } catch (SQLException exception) {
            System.out.println("Database error while reading orders: " + exception.getMessage());
        }

        return orders;
    }

    public void showOrders() {
        List<OrderSummary> orders = getAllOrders();

        System.out.println("\n=== ORDERS LIST ===");

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.printf("%-5s %-20s %-20s %-10s %-10s %-12s %-12s%n",
                "ID", "CUSTOMER", "PRODUCT", "PRICE", "QUANTITY", "DATE", "TOTAL");
        System.out.println("---------------------------------------------------------------------------------------");

        for (OrderSummary order : orders) {
            System.out.printf("%-5d %-20s %-20s %-10s %-10d %-12s %-12s%n",
                    order.getId(),
                    order.getCustomerName(),
                    order.getProductName(),
                    formatMoney(order.getPrice()),
                    order.getQuantity(),
                    order.getOrderDate(),
                    formatMoney(order.getTotalValue()));
        }
    }

    public void showOrdersByCustomer(String customerName) {
        String sql = """
                SELECT
                    orders.id,
                    customers.name AS customer_name,
                    products.name AS product_name,
                    products.price,
                    orders.quantity,
                    orders.order_date,
                    products.price * orders.quantity AS total_value
                FROM orders
                JOIN customers ON orders.customer_id = customers.id
                JOIN products ON orders.product_id = products.id
                WHERE customers.name = ?
                ORDER BY orders.id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, customerName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean found = false;

                System.out.println("\n=== ORDERS FOR CUSTOMER: " + customerName + " ===");

                while (resultSet.next()) {
                    if (!found) {
                        System.out.printf("%-5s %-20s %-20s %-10s %-10s %-12s %-12s%n",
                                "ID", "CUSTOMER", "PRODUCT", "PRICE", "QUANTITY", "DATE", "TOTAL");
                        System.out.println("---------------------------------------------------------------------------------------");
                        found = true;
                    }

                    OrderSummary order = mapOrder(resultSet);

                    System.out.printf("%-5d %-20s %-20s %-10s %-10d %-12s %-12s%n",
                            order.getId(),
                            order.getCustomerName(),
                            order.getProductName(),
                            formatMoney(order.getPrice()),
                            order.getQuantity(),
                            order.getOrderDate(),
                            formatMoney(order.getTotalValue()));
                }

                if (!found) {
                    System.out.println("No orders found for customer: " + customerName);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Database error while filtering orders: " + exception.getMessage());
        }
    }

    public void showOrdersByDate(String date) {
        String sql = """
                SELECT
                    orders.id,
                    customers.name AS customer_name,
                    products.name AS product_name,
                    products.price,
                    orders.quantity,
                    orders.order_date,
                    products.price * orders.quantity AS total_value
                FROM orders
                JOIN customers ON orders.customer_id = customers.id
                JOIN products ON orders.product_id = products.id
                WHERE orders.order_date = ?
                ORDER BY orders.id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean found = false;

                System.out.println("\n=== ORDERS FOR DATE: " + date + " ===");

                while (resultSet.next()) {
                    if (!found) {
                        System.out.printf("%-5s %-20s %-20s %-10s %-10s %-12s %-12s%n",
                                "ID", "CUSTOMER", "PRODUCT", "PRICE", "QUANTITY", "DATE", "TOTAL");
                        System.out.println("---------------------------------------------------------------------------------------");
                        found = true;
                    }

                    OrderSummary order = mapOrder(resultSet);

                    System.out.printf("%-5d %-20s %-20s %-10s %-10d %-12s %-12s%n",
                            order.getId(),
                            order.getCustomerName(),
                            order.getProductName(),
                            formatMoney(order.getPrice()),
                            order.getQuantity(),
                            order.getOrderDate(),
                            formatMoney(order.getTotalValue()));
                }

                if (!found) {
                    System.out.println("No orders found for date: " + date);
                }
            }

        } catch (SQLException exception) {
            System.out.println("Database error while filtering by date: " + exception.getMessage());
        }
    }

    public void showOrdersByDateRange(String startDate, String endDate) {
        String sql = """
                SELECT
                    orders.id,
                    customers.name AS customer_name,
                    products.name AS product_name,
                    products.price,
                    orders.quantity,
                    orders.order_date,
                    products.price * orders.quantity AS total_value
                FROM orders
                JOIN customers ON orders.customer_id = customers.id
                JOIN products ON orders.product_id = products.id
                WHERE orders.order_date BETWEEN ? AND ?
                ORDER BY orders.id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean found = false;

                System.out.println("\n=== ORDERS FROM " + startDate + " TO " + endDate + " ===");

                while (resultSet.next()) {
                    if (!found) {
                        System.out.printf("%-5s %-20s %-20s %-10s %-10s %-12s %-12s%n",
                                "ID", "CUSTOMER", "PRODUCT", "PRICE", "QUANTITY", "DATE", "TOTAL");
                        System.out.println("---------------------------------------------------------------------------------------");
                        found = true;
                    }

                    OrderSummary order = mapOrder(resultSet);

                    System.out.printf("%-5d %-20s %-20s %-10s %-10d %-12s %-12s%n",
                            order.getId(),
                            order.getCustomerName(),
                            order.getProductName(),
                            formatMoney(order.getPrice()),
                            order.getQuantity(),
                            order.getOrderDate(),
                            formatMoney(order.getTotalValue()));
                }

                if (!found) {
                    System.out.println("No orders found in this range.");
                }
            }

        } catch (SQLException exception) {
            System.out.println("Database error while filtering by range: " + exception.getMessage());
        }
    }

    public void updateOrderQuantity(int orderId, int newQuantity) {
        if (!orderExists(orderId)) {
            System.out.println("Order ID does not exist.");
            return;
        }

        if (newQuantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        String sql = "UPDATE orders SET quantity = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, orderId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Order quantity updated successfully.");
            }
        } catch (SQLException exception) {
            System.out.println("Database error while updating order: " + exception.getMessage());
        }
    }

    public void deleteOrder(int orderId) {
        if (!orderExists(orderId)) {
            System.out.println("Order ID does not exist.");
            return;
        }

        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, orderId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Order deleted successfully.");
            }
        } catch (SQLException exception) {
            System.out.println("Database error while deleting order: " + exception.getMessage());
        }
    }

    private boolean orderExists(int orderId) {
        String sql = "SELECT id FROM orders WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            System.out.println("Database error while validating order ID: " + exception.getMessage());
            return false;
        }
    }

    private OrderSummary mapOrder(ResultSet resultSet) throws SQLException {
        int orderId = resultSet.getInt("id");
        String customerName = resultSet.getString("customer_name");
        String productName = resultSet.getString("product_name");
        BigDecimal price = resultSet.getBigDecimal("price");
        int quantity = resultSet.getInt("quantity");
        String orderDate = resultSet.getString("order_date");
        BigDecimal totalValue = resultSet.getBigDecimal("total_value");

        return new OrderSummary(orderId, customerName, productName, price, quantity, orderDate, totalValue);
    }

    private String formatMoney(BigDecimal value) {
        return String.format("%.2f", value);
    }
    public void showTotalSpentByCustomer(String customerName) {
        String sql = """
        SELECT customers.name, SUM(products.price * orders.quantity) AS total_spent
        FROM orders
        JOIN customers ON orders.customer_id = customers.id
        JOIN products ON orders.product_id = products.id
        WHERE customers.name = ?
        GROUP BY customers.name
    """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, customerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println(customerName + " -> Total spent: " +
                        formatMoney(resultSet.getBigDecimal("total_spent")));
            } else {
                System.out.println("No orders found for customer: " + customerName);
            }

        } catch (SQLException exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }
}