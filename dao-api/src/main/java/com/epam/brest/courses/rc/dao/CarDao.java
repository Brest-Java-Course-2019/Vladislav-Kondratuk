package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
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
     * Get all DTO cars.
     *
     * @return DTO cars stream.
     */
    Stream<CarDTO> findAllDTOs();

    /**
     * Get car by ID.
     *
     * @param carId car ID for getting.
     * @return car by ID.
     */
    Optional<Car> findById(Integer carId);

    /**
     * Get DTO car by ID.
     *
     * @param carId DTO car ID for getting.
     * @return DTO car by ID.
     */
    Optional<CarDTO> findDTOById(Integer carId);

    /**
     * Gets DTO cars between certain cost.
     *
     * @param interval cost range for compare.
     * @return DTO cars stream filtered by cost.
     */
    Stream<CarDTO> findDTOsByCost(CarCostInterval interval);

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