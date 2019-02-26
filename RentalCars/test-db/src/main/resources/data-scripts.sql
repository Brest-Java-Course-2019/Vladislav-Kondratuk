INSERT INTO car (carDescription, carNumber, rentalCost) VALUES ('ford focus', 'BY2312', 0.7);
INSERT INTO car (carDescription, carNumber, rentalCost) VALUES ('bmw m3', 'BY4132', 0.85);
INSERT INTO car (carDescription, carNumber, rentalCost) VALUES ('audi a8', 'BY2562', 0.7);

INSERT INTO client (passportNumber, firstName, lastName, addDate) VALUES ('AB42123', 'Alex', 'Petrov', '2019-01-21');
INSERT INTO client (passportNumber, firstName, lastName, addDate) VALUES ('AB75612', 'Anna', 'Hrabrova', '2019-01-24');
INSERT INTO client (passportNumber, firstName, lastName, addDate) VALUES ('AB15436', 'Bogdan', 'Chugunov', '2019-02-02');
INSERT INTO client (passportNumber, firstName, lastName, addDate) VALUES ('AB35431', 'Anton', 'Kuznecov', '2019-02-07');

INSERT INTO rentalOrder (clientId, carId, rentalTime, regDate) VALUES (1, 1, 2, '2019-01-22');
INSERT INTO rentalOrder (clientId, carId, rentalTime, regDate) VALUES (2, 2, 1, '2019-01-26');
INSERT INTO rentalOrder (clientId, carId, rentalTime, regDate) VALUES (3, 3, 3, '2019-02-05');
INSERT INTO rentalOrder (clientId, carId, rentalTime, regDate) VALUES (4, 1, 1, '2019-02-08');
