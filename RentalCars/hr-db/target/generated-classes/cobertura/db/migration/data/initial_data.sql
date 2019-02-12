INSERT INTO rental_order (order_id, order_num, rental_time, total_cost) VALUE (1, 1, 2, 1.7);

INSERT INTO car (car_id, car_name, year_issue, body_type, num_of_seats, transmission, order_id, rental_cost)
    VALUE (1, bmw, 2012, sedan, 4, automatic, 1, 0.85);

INSERT INTO client (client_id, first_name, last_name, order_id)
    VALUE (1, 'Alex', 'Petrov', 1);
