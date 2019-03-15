package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Car;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO Interface of car.
 */
public interface CarDao {

    /**
     * Get all cars.
     *
     * @return cars stream.
     */
    Stream<Car> findAll();

    /**
     * Get car by ID.
     *
     * @param carId car ID for getting.
     * @return car by ID.
     */
    Optional<Car> findById(Integer carId);

    /**
     * Add new car.
     *
     * @param car new car.
     * @return new car.
     */
    Optional<Car> add(Car car);

    /**
     * Update car.
     *
     * @param car car for updating.
     */
    void update(Car car);

    /**
     * Delete car with specified ID.
     *
     * @param carId car ID for delete.
     */
    void delete(int carId);

}