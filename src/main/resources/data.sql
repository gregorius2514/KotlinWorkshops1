--------------------------------
------- insert data section ----
--------------------------------
-- username: admin, password: admin
INSERT INTO users (id, email, first_name, last_name, password)
VALUES (users_seq.nextval, 'admin@example.com', 'admin', 'admin', '$2y$12$eriM5C1/zX/X.3k3Fnz1luX7tGWB4K6HgASwXf5owapJzsVqg7Opu');

INSERT INTO roles (id, name)
VALUES (roles_seq.nextval, 'ADMIN');

INSERT INTO roles (id, name)
VALUES (roles_seq.nextval, 'PARTICIPANT');

INSERT INTO roles (id, name)
VALUES (roles_seq.nextval, 'ORGANIZER');

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id from users where email = 'admin@example.com'), (SELECT id from roles where name = 'ADMIN'));

Insert into RUN VALUES(0, '2020-01-01','test description', 1, 'test run', 10, 10, '10:00:00', 1)
