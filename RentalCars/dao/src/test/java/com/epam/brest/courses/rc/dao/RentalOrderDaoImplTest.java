package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
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
import java.sql.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback
class RentalOrderDaoImplTest {

    private static final int FULL_RENTAL_ORDER_LIST = 4;
    private static final int ORDER_ID = 2;
    private static final BigDecimal RENTAL_TIME = BigDecimal.valueOf(1);
    private static final BigDecimal NEW_RENTAL_TIME = BigDecimal.valueOf(2);
    private static final String REG_DATE = "2019-01-26";
    private static final int CLIENT_ID = 2;
    private static final int CAR_ID = 2;
    private static final int NEW_CLIENT_ID = 2;
    private static final int NEW_CAR_ID = 1;
    private static final String NEW_REG_DATE = "2019-02-12";
    private static final String UNEXPECTED_REG_DATE = "2002-02-13";
    @Autowired
    private RentalOrderDao rentalOrderDao;

    @Test
    void findAll() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        assertNotNull(orders);
    }

    @Test
    void findAllCheckCount() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        assertNotNull(orders);
        assertEquals(FULL_RENTAL_ORDER_LIST, orders.count());
    }

    @Test
    void findById() {
        RentalOrder order = rentalOrderDao.findById(2).get();
        assertNotNull(order);
        assertEquals(CLIENT_ID, order.getClientId().intValue());
        assertEquals(CAR_ID, order.getCarId().intValue());
        assertEquals(ORDER_ID, order.getOrderId().intValue());
        assertEquals(RENTAL_TIME, order.getRentalTime());
        assertEquals(Date.valueOf(REG_DATE), order.getRegDate());
    }

    @Test
    void create() {
        Stream<RentalOrder> orderBeforeInsert = rentalOrderDao.findAll();

        RentalOrder order = new RentalOrder();
        order.setClientId(NEW_CLIENT_ID);
        order.setCarId(NEW_CAR_ID);
        order.setRentalTime(NEW_RENTAL_TIME);
        order.setRegDate(Date.valueOf(NEW_REG_DATE));
        RentalOrder newOrder = rentalOrderDao.add(order).get();
        assertNotNull(newOrder.getOrderId());

        Stream<RentalOrder> orderAfterInsert = rentalOrderDao.findAll();
        assertEquals(1,orderAfterInsert.count() - orderBeforeInsert.count());
    }

    @Test
    void createDuplicateDepartment() {
        RentalOrder order2 = new RentalOrder();
        order2.setClientId(NEW_CLIENT_ID);
        order2.setCarId(NEW_CAR_ID);
        order2.setRentalTime(NEW_RENTAL_TIME);
        order2.setRegDate(Date.valueOf(NEW_REG_DATE));
        RentalOrder newOrder = rentalOrderDao.add(order2).get();
        assertNotNull(newOrder.getOrderId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rentalOrderDao.add(order2);
        });
    }

    @Test
    void update() {
        RentalOrder order = new RentalOrder();
        order.setClientId(NEW_CLIENT_ID);
        order.setCarId(NEW_CAR_ID);
        order.setRentalTime(NEW_RENTAL_TIME);
        order.setRegDate(Date.valueOf(NEW_REG_DATE));
        RentalOrder newOrder = rentalOrderDao.add(order).get();
        assertNotNull(newOrder.getOrderId());

        order.setClientId(NEW_CLIENT_ID + 1);
        order.setCarId(NEW_CAR_ID + 1);
        order.setRentalTime(NEW_RENTAL_TIME.add(BigDecimal.valueOf(5)));
        order.setRegDate(Date.valueOf(UNEXPECTED_REG_DATE));
        rentalOrderDao.update(order);

        RentalOrder updatedOrder = rentalOrderDao.findById(order.getOrderId()).get();

        assertEquals(NEW_CLIENT_ID + 1, updatedOrder.getClientId().intValue());
        assertEquals(NEW_CAR_ID + 1, updatedOrder.getCarId().intValue());
        assertEquals(NEW_RENTAL_TIME.add(BigDecimal.valueOf(5)), updatedOrder.getRentalTime());
        assertEquals(Date.valueOf(UNEXPECTED_REG_DATE), updatedOrder.getRegDate());
    }

    @Test
    void delete() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        RentalOrder order = orders.findFirst().get();
        rentalOrderDao.delete(order.getOrderId());

        Assertions.assertThrows(DataAccessException.class, () -> {
            rentalOrderDao.findById(order.getOrderId());
        });
    }
}
