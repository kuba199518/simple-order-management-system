DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          price NUMERIC(10,2) NOT NULL CHECK (price > 0)
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id INT NOT NULL REFERENCES customers(id),
                        product_id INT NOT NULL REFERENCES products(id),
                        quantity INT NOT NULL CHECK (quantity > 0),
                        order_date DATE NOT NULL
);