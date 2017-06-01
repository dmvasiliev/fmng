create table Users
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  loginName varchar (255) NOT NULL UNIQUE,
  loginPassword varchar (255) NOT NULL,
  CustomerId varchar (255),
  SupplierId varchar (255)
);

INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('dima', 'di', 1, NULL);
INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('lena', 'le', NULL, 1);
INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('gena', 'ge', 1, NULL);
INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('oleg', 'ol', 2, NULL);
INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('egor', 'eg', NULL , 2);
INSERT INTO Users (loginName, loginPassword, CustomerId, SupplierId) VALUES ('admin', 'ad', NULL , NULL );

# ---------------------------------------------------------------------------------------------------------------------

create table Customers
(
  CustomerId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name varchar (255) NOT NULL
);

INSERT INTO Customers (name) VALUES ('21vek.by');
INSERT INTO Customers (name) VALUES ('tnt.by');
INSERT INTO Customers (name) VALUES ('shop.by');

# ---------------------------------------------------------------------------------------------------------------------

create table Suppliers
(
  SupplierId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name varchar (255) NOT NULL
);

INSERT INTO Customers (name) VALUES ('samsung');
INSERT INTO Customers (name) VALUES ('lg');
INSERT INTO Customers (name) VALUES ('atlant');
