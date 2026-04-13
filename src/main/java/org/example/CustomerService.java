package org.example;

import org.example.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public boolean customerExists(int customerId) {
        String sql = "SELECT id FROM customers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            System.out.println("Database error while validating customer ID: " + exception.getMessage());
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT id, name FROM customers ORDER BY id";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
        } catch (SQLException exception) {
            System.out.println("Database error while reading customers: " + exception.getMessage());
        }

        return customers;
    }

    public void showCustomers() {
        List<Customer> customers = getAllCustomers();

        System.out.println("\n=== CUSTOMERS ===");

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.printf("%-5s %-25s%n", "ID", "NAME");
        System.out.println("--------------------------------");

        for (Customer customer : customers) {
            System.out.printf("%-5d %-25s%n", customer.getId(), customer.getName());
        }
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new Customer(id, name);
    }
}
