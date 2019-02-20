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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback
class RentalOrderDaoImplTest {

    private static final int FULL_RENTAL_ORDER_LIST = 4;
    private static final int ORDER_ID = 2;
    private static final int ORDER_NUMBER = 224;
    private static final BigDecimal RENTAL_TIME = BigDecimal.valueOf(1);
    private static final BigDecimal TOTAL_COST = BigDecimal.valueOf(0.85);
    private static final int NEW_ORDER_NUMBER = 543;
    private static final BigDecimal NEW_RENTAL_TIME = BigDecimal.valueOf(2);
    private static final BigDecimal NEW_TOTAL_COST = BigDecimal.valueOf(1.7);
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
        assertEquals(ORDER_ID, order.getOrderId().intValue());
        assertEquals(ORDER_NUMBER, order.getOrderNumber().intValue());
        assertEquals(RENTAL_TIME, order.getRentalTime());
        assertEquals(TOTAL_COST, order.getTotalCost());
    }

    @Test
    void create() {
        Stream<RentalOrder> orderBeforeInsert = rentalOrderDao.findAll();

        RentalOrder order = new RentalOrder();
        order.setOrderNumber(NEW_ORDER_NUMBER);
        order.setRentalTime(NEW_RENTAL_TIME);
        order.setTotalCost(NEW_TOTAL_COST);
        RentalOrder newOrder = rentalOrderDao.add(order).get();
        assertNotNull(newOrder.getOrderId());

        Stream<RentalOrder> orderAfterInsert = rentalOrderDao.findAll();
        assertEquals(1,orderAfterInsert.count() - orderBeforeInsert.count());
    }

    @Test
    void createDuplicateDepartment() {
        RentalOrder order2 = new RentalOrder();
        order2.setOrderNumber(NEW_ORDER_NUMBER);
        order2.setRentalTime(NEW_RENTAL_TIME);
        order2.setTotalCost(NEW_RENTAL_TIME);
        RentalOrder newOrder = rentalOrderDao.add(order2).get();
        assertNotNull(newOrder.getOrderId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rentalOrderDao.add(order2);
        });
    }

    @Test
    void update() {
        RentalOrder order = new RentalOrder();
        order.setOrderNumber(NEW_ORDER_NUMBER);
        order.setRentalTime(NEW_RENTAL_TIME);
        order.setTotalCost(NEW_TOTAL_COST);
        RentalOrder newOrder = rentalOrderDao.add(order).get();
        assertNotNull(newOrder.getOrderId());

        order.setOrderNumber(NEW_ORDER_NUMBER + 1);
        order.setRentalTime(NEW_RENTAL_TIME.add(BigDecimal.valueOf(5)));
        order.setTotalCost(NEW_TOTAL_COST.add(BigDecimal.valueOf(0.21)));
        rentalOrderDao.update(order);

        RentalOrder updatedOrder = rentalOrderDao.findById(order.getOrderId()).get();

        assertEquals(NEW_ORDER_NUMBER + 1, updatedOrder.getOrderNumber().intValue());
        assertEquals(NEW_RENTAL_TIME.add(BigDecimal.valueOf(5)), updatedOrder.getRentalTime());
        assertEquals(NEW_TOTAL_COST.add(BigDecimal.valueOf(0.21)), updatedOrder.getTotalCost());
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
