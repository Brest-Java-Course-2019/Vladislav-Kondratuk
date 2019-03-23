package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.CarDao;
import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of CarService. Gets data from dao and database.
 */
@Transactional
public class CarServiceImpl implements CarService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImpl.class);

    /**
     * DAO.
     */
    private CarDao dao;

    /**
     * Service constructor.
     * @param dao dao of car objects.
     */
    public CarServiceImpl(CarDao dao) {
        this.dao = dao;
    }

    /**
     * Method findAll gets all cars.
     *
     * @return stream of all cars.
     */
    @Override
    public Stream<Car> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll();    }

    /**
     * Method findAllDTOs gets all cars DTO.
     *
     * @return stream of all cars DTO.
     */
    @Override
    public Stream<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs();
    }

    /**
     * Method findById get car by ID.
     *
     * @return car by ID.
     */
    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        return dao.findById(carId);
    }

    /**
     * Method findById get car DTO by ID.
     *
     * @return car DTO by ID.
     */
    @Override
    public Optional<CarDTO> findDTOById(Integer carId) {
        LOGGER.debug("findDTOById({})", carId);
        return dao.findDTOById(carId);
    }

    /**
     * Method findDTOsByCost get cars by cost interval.
     *
     * @param interval cost range for compare.
     * @return cars by cost interval.
     */
    @Override
    public Stream<CarDTO> findDTOsByCost(CarCostInterval interval) {
        LOGGER.debug("findDTOsByCost({})", interval);
        return dao.findDTOsByCost(interval);
    }

    /**
     * Method add new car.
     *
     * @param car new car.
     * @return new car.
     */
    @Override
    public Optional<Car> add(Car car) {
        LOGGER.debug("add({})", car);
        return dao.add(car);
    }

    /**
     * Method update car.
     *
     * @param car car for updating.
     */
    @Override
    public void update(Car car) {
        LOGGER.debug("update({})", car);
        dao.update(car);
    }

    /**
     * Method delete car by ID.
     *
     * @param carId car ID for delete.
     */
    @Override
    public void delete(int carId) {
        LOGGER.debug("delete({})", carId);
        dao.delete(carId);
    }
}
