TRUNCATE TABLE orders, customers, products RESTART IDENTITY CASCADE;

INSERT INTO customers (name, email) VALUES
                                        ('John Smith', 'john.smith@example.com'),
                                        ('Emily Brown', 'emily.brown@example.com'),
                                        ('Michael Johnson', 'michael.johnson@example.com'),
                                        ('Anna Kowalski', 'anna.kowalski@example.com');

INSERT INTO products (name, price) VALUES
                                       ('Laptop', 3000.00),
                                       ('Mouse', 100.00),
                                       ('Keyboard', 200.00),
                                       ('Monitor', 1200.00);

INSERT INTO orders (customer_id, product_id, quantity, order_date) VALUES
                                                                       (1, 1, 1, CURRENT_DATE),
                                                                       (2, 2, 3, CURRENT_DATE),
                                                                       (3, 3, 2, CURRENT_DATE),
                                                                       (4, 4, 1, CURRENT_DATE);