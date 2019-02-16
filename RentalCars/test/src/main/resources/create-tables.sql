DROP TABLE IF EXISTS rentalOrder;
CREATE TABLE rentalOrder (
  orderId INT NOT NULL AUTO_INCREMENT,
  orderNumber INT NOT NULL,
--reg_date CURRENT_TIMESTAMP(2) NULL,
  rentalTime DECIMAL(2) NOT NULL DEFAULT 0,
  totalCost DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (orderId)
);

DROP TABLE IF EXISTS car;
CREATE TABLE car (
  carId INT NOT NULL AUTO_INCREMENT,
  carName VARCHAR(100) NOT NULL,
  yearIssue INT(4) NOT NULL,
  numberOfSeats INT(2) NULL,
  transmission VARCHAR(20) NULL,
  rentalCost DECIMAL(10,2) NOT NULL DEFAULT 0,
  orderId INT,
  PRIMARY KEY (carId),
  FOREIGN KEY (orderId) REFERENCES rentalOrder(orderId)
);

DROP TABLE IF EXISTS client;
CREATE TABLE client (
  clientId INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(45)NOT NULL,
  lastName VARCHAR(45) NOT NULL,
-- add_date CURRENT_TIMESTAMP(2) NULL,
  orderId INT,
  PRIMARY KEY (clientId),
  FOREIGN KEY (orderId) REFERENCES rentalOrder(orderId)
);