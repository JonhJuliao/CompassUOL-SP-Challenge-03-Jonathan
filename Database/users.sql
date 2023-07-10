CREATE DATABASE challenge3_authorization;

CREATE TABLE roles(
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

CREATE TABLE users(
id SERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL
);

CREATE TABLE users_roles(
user_id INT,
role_id INT,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (role_id) REFERENCES roles(id),
PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (id, name) VALUES
(1, 'ADMIN'),
(2, 'OPERATOR');

INSERT INTO users (id, first_name, last_name, email, password) VALUES
(1,'Claudio', 'Pereira', 'claudioadmin@gmail.com', '$2a$12$CoaQV1p/1mxkOJ8LBbTCDeefZbSzoIQkJRvPjjwk6qOvYW2lwWmpO'),
(2,'Antnio)', 'Oliveira', 'antoniouser@gmail.com', '$2a$12$PU7Z1uj17OzWqny9Wf0M8eJ2EZo9lIW5HNigGvFA5E62qXDUKR93C
');

INSERT INTO users_roles(user_id, role_id) VALUES
(1, 1),
(1, 2),
(2, 2);