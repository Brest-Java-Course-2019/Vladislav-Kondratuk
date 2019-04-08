package com.epam.brest.courses.rc.web_app.consumers;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.service.CarService;
import com.epam.brest.courses.rc.web_app.CarController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class CarRestConsumer implements CarService to fully compatible with rest service.
 */
public class CarRestConsumer implements CarService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    /**
     * Request url
     */
    private String url;

    /**
     * Rest template
     */
    private RestTemplate restTemplate;

    /**
     * CarRestConsumer constructor.
     * @param url request url
     * @param restTemplate  rest Template
     */
    public CarRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * findAll() method gets list of cars through rest service.
     * @return body of response entity cars records
     */
    @Override
    public List<Car> findAll() {
        LOGGER.debug("findAll");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<Car>) responseEntity.getBody();
    }

    /**
     * findAllDTOs() method gets list of cars DTO through rest service.
     * @return body of response entity cars DTO.
     */
    @Override
    public List<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all-dto", List.class);
        return  (List<CarDTO>) responseEntity.getBody();
    }

    /**
     * findById() method gets car by ID through rest service.
     * @param carId car ID for getting.
     * @return body of response entity car by ID.
     */
    @Override
    public Car findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        ResponseEntity<Car> responseEntity = restTemplate.getForEntity(url + "/car/" + carId,
                Car.class);
        return responseEntity.getBody();
    }

    /**
     * findDTOById() method gets car DTO by ID through rest service.
     * @param carId DTO car ID for getting.
     * @return body of response entity car DTO by ID.
     */
    @Override
    public CarDTO findDTOById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        ResponseEntity<CarDTO> responseEntity = restTemplate.getForEntity(url + "/car/" + carId,
                CarDTO.class);
        return responseEntity.getBody();
    }

    /**
     * findDTOsByCost() method gets cars DTO by cost interval through rest service.
     * @param startCost interval start cost.
     * @param endCost interval end cost.
     * @return body of response entity cars DTO by cost interval.
     */
    @Override
    public List<CarDTO> findDTOsByCost(BigDecimal startCost, BigDecimal endCost) {
        LOGGER.debug("findDTOsByCost()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/dto/" + startCost
                + "/" + endCost, List.class);
        return  (List<CarDTO>) responseEntity.getBody();
    }

    /**
     * add() method create new car through rest service.
     * @param car new car.
     */
    @Override
    public void add(Car car) {
        LOGGER.debug("add({})", car);
        restTemplate.postForEntity(url, car, Car.class);
    }

    /**
     * update() method update car through rest service.
     * @param car car for updating.
     */
    @Override
    public void update(Car car) {
        LOGGER.debug("update({})", car);
        restTemplate.put(url, car);

    }

    /**
     * delete() method delete car through rest service.
     * @param carId car ID for delete.
     */
    @Override
    public void delete(int carId) {
        LOGGER.debug("delete({})", carId);
        restTemplate.delete(url + "/car/" + carId);
    }
}
