package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.CarDao;
import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private static final int ONCE = 1;
    private static final int CAR_ID = 1;
    private static final String CAR_DESCRIPTION = "ford";
    private static final String CAR_NUMBER = "BY2312";
    private static final BigDecimal RENTAL_COST = BigDecimal.valueOf(70);
    private static final int CAR_ID_DTO = 2;
    private static final String CAR_DESCRIPTION_DTO = "bmw";
    private static final BigDecimal RENTAL_COST_DTO = BigDecimal.valueOf(75);
    private static final int NUMBER_OF_ORDERS_DTO = 1;
    private static final int EXPECTED_NUMBER_OF_CARS = 1;
    private static final BigDecimal START_COST = BigDecimal.valueOf(70);
    private static final BigDecimal END_COST = BigDecimal.valueOf(80);

    private CarService service;

    private CarDao dao;

    private Car car = new Car();

    private CarDTO carDTO =  new CarDTO();

    private Car createCar() {
        car.setCarId(CAR_ID);
        car.setCarDescription(CAR_DESCRIPTION);
        car.setCarNumber(CAR_NUMBER);
        car.setRentalCost(RENTAL_COST);
        return car;
    }

    private CarDTO createCarDTO() {
        carDTO.setCarId(CAR_ID_DTO);
        carDTO.setCarDescription(CAR_DESCRIPTION_DTO);
        carDTO.setRentalCost(RENTAL_COST_DTO);
        carDTO.setNumberOfOrders(NUMBER_OF_ORDERS_DTO);
        return carDTO;
    }

    @BeforeEach
    void setup() {
        dao = Mockito.mock(CarDao.class);
        service = new CarServiceImpl(dao);
    }

    @AfterEach
    void verifyAndReset() {
        Mockito.verifyNoMoreInteractions(dao);
        Mockito.reset(dao);
    }

    @Test
    void shouldFindAllCars() {
        LOGGER.debug("run test shouldFindAllCars()");

        Mockito.when(dao.findAll()).thenReturn(Stream.of(createCar()));

        List<Car> carList = service.findAll();
        assertEquals(EXPECTED_NUMBER_OF_CARS, carList.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllDTOs() {
        LOGGER.debug("run test shouldFindAllDTOs()");

        Mockito.when(dao.findAllDTOs()).thenReturn(Stream.of(createCarDTO()));

        List<CarDTO> carDTOList = service.findAllDTOs();
        assertEquals(EXPECTED_NUMBER_OF_CARS, carDTOList.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindCarById() {
        LOGGER.debug("run test shouldFindCarById()");

        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(createCar()));

        Car car = service.findById(CAR_ID);
        assertEquals(car, createCar());

        Mockito.verify(dao, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOById() {
        LOGGER.debug("run test shouldFindDTOById()");

        Mockito.when(dao.findDTOById(Mockito.anyInt())).thenReturn(Optional.of(createCarDTO()));

        CarDTO carDTO = service.findDTOById(CAR_ID_DTO);
        assertEquals(carDTO, createCarDTO());

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOsByCost() {
        LOGGER.debug("run test shouldFindDTOsByCost()");

        Mockito.when(dao.findDTOsByCost(Mockito.any(CarCostInterval.class)))
                .thenReturn(Stream.of(createCarDTO()));

        List<CarDTO> carDTOList = service.findDTOsByCost(START_COST, END_COST);
        assertNotNull(carDTOList);

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOsByCost(Mockito.any(CarCostInterval.class));
    }

    @Test
    void shouldAddNewCar() {
        LOGGER.debug("run test shouldAddNewCar()");

        Mockito.when(dao.add(Mockito.any(Car.class))).thenReturn(Optional.of(createCar()));
        service.add(createCar());
        Mockito.verify(dao, Mockito.times(ONCE)).add(createCar());
    }

    @Test
    void shouldUpdateCar() {
        LOGGER.debug("run test shouldUpdateCar()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).update(Mockito.any(Car.class));
        service.update(createCar());
        Mockito.verify(dao, Mockito.times(ONCE)).update(createCar());
    }

    @Test
    void shouldDeleteCar() {
        LOGGER.debug("run test shouldDeleteCar()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).delete(CAR_ID);
        Mockito.when(dao.findById(CAR_ID)).thenReturn(Optional.of(createCar()));
        service.delete(CAR_ID);
        Mockito.verify(dao,Mockito.times(ONCE)).delete(CAR_ID);
    }
}