--CREATE TABLE users (
--     username VARCHAR(50) NOT NULL,
--     password VARCHAR(68) NOT NULL,
--     enabled  BOOLEAN NOT NULL,
--     PRIMARY KEY(username)
--);
--
--CREATE TABLE authorities (
--     username  VARCHAR(50) NOT NULL,
--     authority VARCHAR(68) NOT NULL,
--     FOREIGN KEY (username) REFERENCES users(username)
--);
--------------------------------
------- insert data section ----
--------------------------------
-- username: admin, password: admin
INSERT INTO users (id, email, first_name, last_name, password)
VALUES (1000, 'admin@test.pl', 'admin', 'admin', '$2y$12$eriM5C1/zX/X.3k3Fnz1luX7tGWB4K6HgASwXf5owapJzsVqg7Opu');

INSERT INTO roles (id, name)
VALUES (1000, 'ADMIN');

INSERT INTO roles (id, name)
VALUES (1001, 'USER');

INSERT INTO user_role
VALUES (1000, 1000);
