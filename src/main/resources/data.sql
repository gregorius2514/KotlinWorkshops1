CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
   
   INSERT INTO users (username, password, enabled) VALUES('manager','admin',1);
   
   CREATE TABLE authorities
   (
		username VARCHAR(50) NOT NULL,
        role VARCHAR(68) NOT NULL,
        FOREIGN KEY (username) REFERENCES users(username)
   );
   
   INSERT INTO authorities VALUES('manager','ROLE_MANAGER');
   INSERT INTO authorities VALUES('manager','ROLE_USER');
   
