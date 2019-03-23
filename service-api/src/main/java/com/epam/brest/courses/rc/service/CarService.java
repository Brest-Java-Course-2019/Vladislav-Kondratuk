package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service interface of car.
 */
public interface CarService {

    /**
     * Get all cars from DAO.
     *
     * @return cars stream.
     */
    Stream<Car> findAll();

    /**
     * Get all DTO cars from DAO.
     *
     * @return DTO cars stream.
     */
    Stream<CarDTO> findAllDTOs();

    /**
     * Get car by ID from DAO.
     *
     * @param carId car ID for getting.
     * @return car by ID.
     */
    Optional<Car> findById(Integer carId);

    /**
     * Get DTO car by ID from DAO.
     *
     * @param carId DTO car ID for getting.
     * @return DTO car by ID.
     */
    Optional<CarDTO> findDTOById(Integer carId);

    /**
     * Gets DTO cars between certain cost from DAO.
     *
     * @param interval cost range for compare.
     * @return DTO cars stream filtered by cost.
     */
    Stream<CarDTO> findDTOsByCost(CarCostInterval interval);

    /**
     * Add new car to DAO.
     *
     * @param car new car.
     * @return new car.
     */
    Optional<Car> add(Car car);

    /**
     * Update car in DAO.
     *
     * @param car car for updating.
     */
    void update(Car car);

    /**
     * Delete car with specified ID from DAO.
     *
     * @param carId car ID for delete.
     */
    void delete(int carId);
}
