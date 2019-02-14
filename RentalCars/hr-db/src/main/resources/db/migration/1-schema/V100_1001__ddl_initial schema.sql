-- Schema RC
DROP TABLE IF EXISTS rental_order;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS client;

-- rental_order
CREATE TABLE rental (
  order_id SERIAL NOT NULL,
  order_num INTEGER NULL,
--reg_date CURRENT_TIMESTAMP(2) NULL,
  rental_time DECIMAL(2) NOT NULL DEFAULT 0,
  total_cost DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (order_id)
);

-- car
CREATE TABLE car (
  car_id SERIAL NOT NULL,
  car_name VARCHAR(100) NOT NULL,
  year_issue INT(4) NULL DEFAULT 1990-2019,
  body_type VARCHAR(20) NULL,
  num_of_seats INT(2) NULL,
  transmission VARCHAR(20) NULL,
  order_id INTEGER NOT NULL,
  rental_cost DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (car_id),
    CONSTRAINT car_to_rental_order_fk
      FOREIGN KEY (order_id)
      REFERENCES rental_order (order_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

-- client
CREATE TABLE client (
  client_id SERIAL NOT NULL,
  first_name VARCHAR(45) NULL,
  last_name VARCHAR(45) NULL,
-- add_date CURRENT_TIMESTAMP(2) NULL,
  order_id INTEGER NOT NULL,
  PRIMARY KEY (client_id),
      CONSTRAINT client_to_rental_order_fk
        FOREIGN KEY (order_id)
        REFERENCES rental_order (order_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);
