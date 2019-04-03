package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.model.Car;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface of car.
 */
public interface CarService {

    /**
     * Get all cars from DAO.
     *
     * @return cars list.
     */
    List<Car> findAll();

    /**
     * Get all DTO cars from DAO.
     *
     * @return DTO cars list.
     */
    List<CarDTO> findAllDTOs();

    /**
     * Get car by ID from DAO.
     *
     * @param carId car ID for getting.
     * @return car by ID.
     */
    Car findById(Integer carId);

    /**
     * Get DTO car by ID from DAO.
     *
     * @param carId DTO car ID for getting.
     * @return DTO car by ID.
     */
    CarDTO findDTOById(Integer carId);

    /**
     * Gets DTO cars between certain cost from DAO.
     *
     * @param startCost interval start cost.
     * @param endCost interval end cost.
     * @return DTO cars list filtered by cost.
     */
    List<CarDTO> findDTOsByCost(BigDecimal startCost, BigDecimal endCost);

    /**
     * Add new car to DAO.
     *
     * @param car new car.
     */
    void add(Car car);

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
