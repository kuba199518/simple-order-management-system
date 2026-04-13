package org.example.model;

import java.math.BigDecimal;

public class OrderSummary {

    private final int id;
    private final String customerName;
    private final String productName;
    private final BigDecimal price;
    private final int quantity;
    private final String orderDate;
    private final BigDecimal totalValue;

    public OrderSummary(
            int id,
            String customerName,
            String productName,
            BigDecimal price,
            int quantity,
            String orderDate,
            BigDecimal totalValue
    ) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.totalValue = totalValue;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }
}
