package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
class RentalOrderDaoImplTest {

    private static final int FULL_RENTAL_ORDER_LIST = 4;
    private static final int ORDER_ID = 2;
    private static final int ORDER_NUMBER = 224;
    private static final BigDecimal RENTAL_TIME = BigDecimal.valueOf(1);
    private static final BigDecimal TOTAL_COST = BigDecimal.valueOf(0.85);
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
        Assertions.assertEquals(RENTAL_TIME, order.getRentalTime());
        Assertions.assertEquals(TOTAL_COST, order.getTotalCost());
    }
}
