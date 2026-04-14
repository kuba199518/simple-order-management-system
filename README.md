Simple Order Management System

Simple console-based backend application written in Java for managing customers, products, and orders using PostgreSQL and JDBC.

Project Purpose

This project was created as a junior backend portfolio project.

It demonstrates:

working with a relational database (PostgreSQL)
writing SQL queries (SELECT, INSERT, JOIN, GROUP BY, SUM)
handling user input and validation
implementing business logic
structuring a Java backend application without frameworks
Tech Stack
Java 21
Maven
PostgreSQL
JDBC
Console CLI application
Features
display all customers
display all products
create a new order
validate:
customer_id exists
product_id exists
quantity > 0
update order quantity
delete order
display all orders using SQL JOIN
filter orders by customer
filter orders by date
filter orders by date range
show total spent by customer (SUM + GROUP BY)
show top selling products (ranking)
Project Structure
src
└── main
└── java
└── org
└── example
├── DatabaseConnection.java
├── Main.java
├── CustomerService.java
├── ProductService.java
├── OrderService.java
└── model
├── Customer.java
├── Product.java
└── OrderSummary.java
Database Structure
customers
id (PRIMARY KEY)
name
email (UNIQUE)
products
id (PRIMARY KEY)
name
price
orders
id (PRIMARY KEY)
customer_id (FOREIGN KEY)
product_id (FOREIGN KEY)
quantity
order_date
How to Run
1. Install PostgreSQL

Make sure PostgreSQL is installed and running.

2. Create database
   CREATE DATABASE order_db;
3. Create tables

Run the file:
schema.sql

4. Insert sample data

Run:
seed.sql

5. Set environment variables

Windows (PowerShell):

setx DB_USER postgres
setx DB_PASSWORD your_password

Restart IntelliJ IDEA after setting variables.

6. Run application

Open project in IntelliJ IDEA and run:

Main.java

Example Use Cases
create a new order for an existing customer and product
list all orders with customer name, product name, quantity, date, and total value
filter orders by customer or date
show total spending for a selected customer
show top selling products
update quantity in an existing order
remove an order by ID
Backend Concepts Demonstrated
JDBC (PreparedStatement, ResultSet)
SQL JOIN operations
input validation and business logic
filtering (customer, date, date range)
aggregation queries (SUM, GROUP BY)
reporting logic (total spending, ranking)
Future Improvements
REST API (Spring Boot)
frontend UI (React)
authentication system
Docker support
Notes
the project uses plain JDBC (no ORM)
no frameworks like Spring Boot are used
credentials are handled via environment variables
designed as a simple backend portfolio project