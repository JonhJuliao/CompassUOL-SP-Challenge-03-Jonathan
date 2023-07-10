CREATE DATABASE challenge3;

CREATE TABLE categories(
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

CREATE TABLE products(
id SERIAL PRIMARY KEY,
date DATE,
name VARCHAR(100) NOT NULL,
description VARCHAR(255),
price DECIMAL(10,2) NOT NULL,
img_url VARCHAR(255)
);

CREATE TABLE products_categories(
product_id INT,
category_id INT,
FOREIGN KEY (product_id) REFERENCES products(id),
FOREIGN KEY (category_id) REFERENCES categories(id),
PRIMARI KEY (product_id, category_id)
);

INSERT INTO categories (id, name) VALUES
(1, 'Category 1'),
(2, 'Category 2'),
(3, 'Category 3');

INSERT INTO products (id, date, name, description, price, img_url) VALUES
(1, '2023-07-10', 'Produto 1', 'Description 1', 50.00, 'https://imagurl.com/image1'),
(2, '2023-07-10', 'Produto 1', 'Description 1', 50.00, 'https://imagurl.com/image2'),
(3, '2023-07-10', 'Produto 1', 'Description 1', 50.00, 'https://imagurl.com/image3');

INSERT INTO products_categories (product_id, category_id) VALUES
(1, 1),
(2, 1),
(2, 3),
(3, 1),
(3, 2),
(3, 3);