package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.CarDao;
import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
     * @return list of all cars.
     */
    @Override
    public List<Car> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll().collect(Collectors.toList());    }

    /**
     * Method findAllDTOs gets all cars DTO.
     *
     * @return list of all cars DTO.
     */
    @Override
    public List<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs().collect(Collectors.toList());
    }

    /**
     * Method findById get car by ID.
     *
     * @return car by ID.
     */
    @Override
    public Car findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        return dao.findById(carId)
                .orElseThrow(() -> new RuntimeException("Failed to get car from DB"));
    }

    /**
     * Method findById get car DTO by ID.
     *
     * @return car DTO by ID.
     */
    @Override
    public CarDTO findDTOById(Integer carId) {
        LOGGER.debug("findDTOById({})", carId);
        return dao.findDTOById(carId)
                .orElseThrow(() -> new RuntimeException("Failed to get car DTO from DB"));
    }

    /**
     * Method findDTOsByCost get cars by cost interval.
     *
     * @param startCost interval start cost.
     * @param endCost interval end cost.
     * @return DTO cars List filtered by cost.
     */
    @Override
    public List<CarDTO> findDTOsByCost(BigDecimal startCost, BigDecimal endCost) {
        LOGGER.debug("findDTOsByCost({}, {})", startCost, endCost);
        return dao.findDTOsByCost(new CarCostInterval(startCost, endCost)).collect(Collectors.toList());
    }

    /**
     * Method add new car.
     *
     * @param car new car.
     */
    @Override
    public void add(Car car) {
        LOGGER.debug("add({})", car);
        dao.add(car);
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
