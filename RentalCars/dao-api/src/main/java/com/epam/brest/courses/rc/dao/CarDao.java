package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Car;

import java.util.Optional;
import java.util.stream.Stream;

public interface CarDao {

    Stream<Car> findAll();

    Optional<Car> findById(Integer carId);

    Optional<Car> add(Car car);

    void update(Car car);

    void delete(int carId);
}
