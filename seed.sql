TRUNCATE TABLE orders RESTART IDENTITY CASCADE;
TRUNCATE TABLE customers RESTART IDENTITY CASCADE;
TRUNCATE TABLE products RESTART IDENTITY CASCADE;

INSERT INTO customers (name) VALUES
                                 ('John Smith'),
                                 ('Emily Brown'),
                                 ('Michael Johnson'),
                                 ('Anna Kowalski');

INSERT INTO products (name, price) VALUES
                                       ('Laptop', 3000.00),
                                       ('Mouse', 100.00),
                                       ('Keyboard', 200.00),
                                       ('Monitor', 1200.00);

INSERT INTO orders (customer_id, product_id, quantity, order_date) VALUES
                                                                       (1, 1, 1, '2026-04-01'),
                                                                       (2, 2, 3, '2026-04-02'),
                                                                       (3, 3, 2, '2026-04-03'),
                                                                       (4, 4, 1, '2026-04-04');