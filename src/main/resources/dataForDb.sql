create table testusers
(
  id INT NOT NULL AUTO_INCREMENT,
  userName varchar (255) NOT NULL,
  userPassword varchar (255) NOT NULL,
  groupe varchar (255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO testusers (userName, userPassword, groupe) VALUES ('Dima', 'di', 'admins');
INSERT INTO testusers (userName, userPassword, groupe) VALUES ('Lesha', 'le', 'customers');
INSERT INTO testusers (userName, userPassword, groupe) VALUES ('Gena', 'ge', 'suppliers');


SELECT * FROM testusers;