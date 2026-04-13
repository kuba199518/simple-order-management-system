# 🧾 Simple Order Management System

Simple console-based backend application written in Java for managing customer orders using PostgreSQL and JDBC.

---

## 🚀 Project Purpose

This project was created as a **junior backend portfolio project**.

It demonstrates:

* working with a relational database (PostgreSQL)
* writing SQL queries (SELECT, INSERT, JOIN)
* handling user input and validation
* implementing basic business logic
* structuring a Java application without frameworks

---

## ⚙️ Tech Stack

* Java 21
* Maven
* PostgreSQL
* JDBC
* Console CLI application

---

## 📦 Features

* display all customers
* display all products
* create a new order
* validate:

   * customer_id exists
   * product_id exists
   * quantity > 0
* edit order quantity
* delete order
* display orders using JOIN:

   * customer name
   * product name
   * price
   * quantity
   * total value

---

## 🗄️ Database Structure

### customers

* id (PK)
* name
* email (NOT NULL, UNIQUE)

### products

* id (PK)
* name
* price

### orders

* id (PK)
* customer_id (FK)
* product_id (FK)
* quantity
* order_date

---

## ▶️ How to Run

### 1. Start PostgreSQL

### 2. Create database

```sql
CREATE DATABASE order_db;
```

### 3. Create tables

```sql
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES customers(id),
    product_id INT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL CHECK (quantity > 0),
    order_date DATE NOT NULL
);
```

### 4. Run seed script

Use the provided `seed.sql` file to populate the database with sample data.

### 5. Configure database connection

Update credentials in `DatabaseConnection.java`:

* database URL
* username
* password

### 6. Run application

Run `Main.java` from IntelliJ IDEA or use Maven:

```bash
mvn compile
mvn exec:java
```

---

## 💡 Example Output

```
ID | Customer        | Product  | Price | Quantity | Total
--------------------------------------------------------
1  | John Smith      | Laptop   | 3000  | 1        | 3000
2  | Emily Brown     | Mouse    | 100   | 3        | 300
```

---

## 🧠 Backend Concepts Demonstrated

* JDBC database connection handling
* SQL queries (SELECT, INSERT, JOIN)
* data validation and error handling
* service-based application structure
* mapping ResultSet to Java objects
* simple business logic implementation

---

## 📌 Notes

* the project uses plain JDBC (no ORM)
* no frameworks like Spring Boot are used
* the code is intentionally simple and educational
* suitable as a junior backend portfolio project
