CREATE TABLE users(
id SERIAL,
email VARCHAR(255),
first_name VARCHAR(255),
last_name VARCHAR(255),
password VARCHAR(255)
);

CREATE TABLE roles(
id SERIAL,
name VARCHAR(255)
);

CREATE TABLE user_role(
id SERIAL,
user_id integer,
role_id integer
);

INSERT INTO users (id, email, first_name, last_name, password)
VALUES (1, 'admin@example.com', 'admin', 'admin', '$2y$12$eriM5C1/zX/X.3k3Fnz1luX7tGWB4K6HgASwXf5owapJzsVqg7Opu');

INSERT INTO roles (id, name)
VALUES (1, 'ADMIN');

INSERT INTO roles (id, name)
VALUES (1, 'PARTICIPANT');

INSERT INTO roles (id, name)
VALUES (1, 'ORGANIZER');

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id from users where email = 'admin@example.com'), (SELECT id from roles where name = 'ADMIN'));

-- Insert into RUN VALUES(0, '2020-01-01','test description', 1, 'test run', 10, 10, '10:00:00', 1)
