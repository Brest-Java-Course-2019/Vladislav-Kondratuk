package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback
class CarDaoImplTest {

    private static final int CAR_ID = 1;
    private static final String NEW_CAR_NUMBER = "BY1234";
    private static final int FULL_CAR_LIST = 3;
    private static final String CAR_DESCRIPTION = "ford focus";
    private static final String CAR_NUMBER = "BY2312";
    private static final BigDecimal RENTAL_COST = BigDecimal.valueOf(0.7).setScale(2, RoundingMode.CEILING);
    private static final String NEW_CAR_DESCRIPTION = "volkswagen passat";
    private static final BigDecimal NEW_RENTAL_COST = BigDecimal.valueOf(0.8).setScale(2, RoundingMode.CEILING);
    @Autowired
    private CarDao carDao;

    @Test
    void findAll() {
        Stream<Car> cars = carDao.findAll();
        assertNotNull(cars);
    }

    @Test
    void findAllCheckCount(){
        Stream<Car> cars = carDao.findAll();
        assertNotNull(cars);
        assertEquals(FULL_CAR_LIST, cars.count());
    }

    @Test
    void findById() {
        Car car = carDao.findById(1).get();
        assertNotNull(car);
        assertEquals(CAR_ID, car.getCarId().intValue());
        assertEquals(CAR_DESCRIPTION, car.getCarDescription());
        assertEquals(CAR_NUMBER, car.getCarNumber());
        assertEquals(RENTAL_COST, car.getRentalCost());
    }

    @Test
    void create() {
        Stream<Car> carsBeforeInsert = carDao.findAll();

        Car car = new Car();
        car.setCarDescription(NEW_CAR_DESCRIPTION);
        car.setCarNumber(NEW_CAR_NUMBER);
        car.setRentalCost(NEW_RENTAL_COST);
        Car newCar = carDao.add(car).get();
        assertNotNull(newCar.getCarId());

        Stream<Car> carsAfterInsert = carDao.findAll();

        assertEquals(1, carsAfterInsert.count() - carsBeforeInsert.count());
    }

    @Test
    void createDuplicateCar() {
        Car car2 = new Car();
        car2.setCarDescription(NEW_CAR_DESCRIPTION);
        car2.setCarNumber(NEW_CAR_NUMBER);
        car2.setRentalCost(NEW_RENTAL_COST);
        Car newCar = carDao.add(car2).get();
        assertNotNull(newCar.getCarId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            carDao.add(car2);
        });
    }

    @Test
    void update() {
        Car car = new Car();
        car.setCarDescription(NEW_CAR_DESCRIPTION);
        car.setCarNumber(NEW_CAR_NUMBER);
        car.setRentalCost(NEW_RENTAL_COST);
        Car newCar = carDao.add(car).get();
        assertNotNull(newCar.getCarId());

        car.setCarDescription(NEW_CAR_DESCRIPTION + "2");
        car.setCarNumber(NEW_CAR_NUMBER + "2");
        car.setRentalCost(NEW_RENTAL_COST.add(BigDecimal.valueOf(2)));
        carDao.update(car);

        Car updatedCar = carDao.findById(car.getCarId()).get();

        assertEquals(NEW_CAR_DESCRIPTION + "2", updatedCar.getCarDescription());
        assertEquals(NEW_CAR_NUMBER + "2", updatedCar.getCarNumber());
        assertEquals(NEW_RENTAL_COST.add(BigDecimal.valueOf(2)), updatedCar.getRentalCost());
    }

    @Test
    void delete() {
        Car car = new Car();
        car.setCarDescription(NEW_CAR_DESCRIPTION);
        car.setCarNumber(NEW_CAR_NUMBER);
        car.setRentalCost(NEW_RENTAL_COST);
        Car newCar = carDao.add(car).get();

        assertNotNull(newCar.getCarId());

        carDao.delete(newCar.getCarId());

        Assertions.assertThrows(DataAccessException.class, () -> {
            carDao.findById(car.getCarId());
        });
    }
}