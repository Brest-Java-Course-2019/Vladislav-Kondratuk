DROP TABLE IF EXISTS car;
CREATE TABLE car (
  carId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  carName VARCHAR(100) NOT NULL,
  yearIssue INT(4) NOT NULL,
  numberOfSeats INT(2) NULL,
  transmission VARCHAR(20) NULL,
  rentalCost DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (carId)
);

DROP TABLE IF EXISTS client;
CREATE TABLE client (
  clientId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(45)NOT NULL,
  lastName VARCHAR(45) NOT NULL,
-- add_date CURRENT_TIMESTAMP(2) NULL,
  PRIMARY KEY (clientId)
);

DROP TABLE IF EXISTS rentalOrder;
CREATE TABLE rentalOrder (
  orderId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  orderNumber INT NOT NULL,
--reg_date CURRENT_TIMESTAMP(2) NULL,
  rentalTime DECIMAL(2) NOT NULL DEFAULT 0,
  totalCost DECIMAL(10,2) NOT NULL DEFAULT 0,
  carId INT,
  clientId INT,
  FOREIGN KEY (clientId) REFERENCES client (clientId),
  FOREIGN KEY (carId) REFERENCES car (carId)
);