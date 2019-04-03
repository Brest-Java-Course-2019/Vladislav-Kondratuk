package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rest Controller for cars.
 */
@RestController
@RequestMapping(value = "/cars")
public class CarRestController implements CarService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    /**
     * Service.
     */
    @Autowired
    private CarService carService;

    /**
     * Gets cars.
     * @return list of cars.
     *
     * curl -X GET -v http://localhost:8088/cars/all
     */
    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Car> findAll() {
        LOGGER.debug("findAll()");
        return carService.findAll();
    }

    /**
     * Gets DTO cars.
     * @return list of DTO cars.
     *
     * curl -X GET -v http://localhost:8088/cars/all-dto
     */
    @Override
    @RequestMapping(value = "/all-dto", method = RequestMethod.GET)
    public List<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return carService.findAllDTOs();
    }

    /**
     * Gets car by ID.
     * @return car by ID.
     *
     * curl -X GET -v http://localhost:8088/cars/car/1
     */
    @Override
    @RequestMapping(value = "/car/{carId}", method = RequestMethod.GET)
    public Car findById(@PathVariable Integer carId) {
        LOGGER.debug("findById({})", carId);
        return carService.findById(carId);
    }

    /**
     * Gets DTO car by ID.
     * @return DTO car by ID.
     *
     * curl -X GET -v http://localhost:8088/cars/dto/1
     */
    @Override
    @RequestMapping(value = "/dto/{carId}", method = RequestMethod.GET)
    public CarDTO findDTOById(@PathVariable Integer carId) {
        LOGGER.debug("findDTOById({})", carId);
        return carService.findDTOById(carId);
    }

    /**
     * Gets DTO cars filtered by cost.
     * @param startCost interval start cost.
     * @param endCost interval end cost.
     * @return DTO cars list filtered by cost.
     *
     * curl -X GET -v http://localhost:8088/cars/dto/60/75
     */
    @Override
    @RequestMapping(value = "/dto/{startCost}/{endCost}", method = RequestMethod.GET)
    public List<CarDTO> findDTOsByCost(@PathVariable(value = "startCost") final BigDecimal startCost,
                                       @PathVariable(value = "endCost") final BigDecimal endCost) {
        LOGGER.debug("findDTOsByCost({}, {})", startCost, endCost);
        return carService.findDTOsByCost(startCost, endCost);
    }

    /**
     * Adds new car.
     * @param car new car.
     *
     *  curl -H "Content-Type: application/json" -X POST -d '{"carId":"4","carDescription":"volkswagen",
     *          "carNumber":"AB4124","rentalCost":"73"}' -v http://localhost:8088/cars
     */
    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Car car) {
        LOGGER.debug("add car({})", car);
        carService.add(car);
    }

    /**
     * Update car.
     * @param car car for updating.
     *
     *  curl -H "Content-Type: application/json" -X PUT -d '{"carId":"4","carDescription":"fiat",
     *          "carNumber":"AB6414","rentalCost":"76"}' -v http://localhost:8088/cars
     */
    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Car car) {
        LOGGER.debug("update car({})", car);
        carService.update(car);
    }

    /**
     * Delete car by ID.
     * @param carId car ID for delete.
     *
     * curl -X DELETE -v http://localhost:8088/cars/car/4
     */
    @Override
    @RequestMapping(value = "/car/{carId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int carId) {
        LOGGER.debug("delete car({})", carId);
        carService.delete(carId);
    }
}
