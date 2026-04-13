package org.example;

import org.example.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public boolean productExists(int productId) {
        String sql = "SELECT id FROM products WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            System.out.println("Database error while validating product ID: " + exception.getMessage());
            return false;
        }
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT id, name, price FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
        } catch (SQLException exception) {
            System.out.println("Database error while reading products: " + exception.getMessage());
        }

        return products;
    }

    public void showProducts() {
        List<Product> products = getAllProducts();

        System.out.println("\n=== PRODUCTS ===");

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.printf("%-5s %-25s %-10s%n", "ID", "NAME", "PRICE");
        System.out.println("----------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d %-25s %-10s%n",
                    product.getId(),
                    product.getName(),
                    formatMoney(product.getPrice()));
        }
    }

    private Product mapProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");

        return new Product(id, name, price);
    }

    private String formatMoney(BigDecimal value) {
        return String.format("%.2f", value);
    }
}
