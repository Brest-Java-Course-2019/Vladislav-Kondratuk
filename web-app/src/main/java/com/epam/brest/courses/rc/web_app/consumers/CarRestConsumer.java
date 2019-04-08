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

public class CarRestConsumer implements CarService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private String url;

    private RestTemplate restTemplate;

    public CarRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Car> findAll() {
        LOGGER.debug("findAll");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<Car>) responseEntity.getBody();
    }

    @Override
    public List<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all-dto", List.class);
        return  (List<CarDTO>) responseEntity.getBody();
    }

    @Override
    public Car findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        ResponseEntity<Car> responseEntity = restTemplate.getForEntity(url + "/car/" + carId,
                Car.class);
        return responseEntity.getBody();
    }

    @Override
    public CarDTO findDTOById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        ResponseEntity<CarDTO> responseEntity = restTemplate.getForEntity(url + "/car/" + carId,
                CarDTO.class);
        return responseEntity.getBody();
    }

    @Override
    public List<CarDTO> findDTOsByCost(BigDecimal startCost, BigDecimal endCost) {
        LOGGER.debug("findDTOsByCost()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/dto/" + startCost
                + "/" + endCost, List.class);
        return  (List<CarDTO>) responseEntity.getBody();
    }

    @Override
    public void add(Car car) {
        LOGGER.debug("add({})", car);
        restTemplate.postForEntity(url, car, Car.class);
    }

    @Override
    public void update(Car car) {
        LOGGER.debug("update({})", car);
        restTemplate.put(url, car);

    }

    @Override
    public void delete(int carId) {
        LOGGER.debug("update({})", carId);
        restTemplate.delete(url + "/car/" + carId);
    }
}
