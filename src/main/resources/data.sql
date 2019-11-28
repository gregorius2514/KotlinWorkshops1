DROP TABLE IF EXISTS users;
 
CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  login VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  active boolean DEFAULT FALSE
);
 
INSERT INTO users(login, password, active) VALUES ('admin', 'YWRtaW4K', TRUE);
