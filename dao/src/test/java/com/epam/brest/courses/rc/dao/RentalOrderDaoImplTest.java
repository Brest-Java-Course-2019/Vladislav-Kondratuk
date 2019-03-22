package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
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
import java.sql.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:dao.xml", "classpath:test-dao.xml", "classpath:test-db.xml"})
@Transactional
@Rollback
class RentalOrderDaoImplTest {

    private static final int ORDER_ID = 2;
    private static final int FULL_RENTAL_ORDER_LIST = 4;
    private static final BigDecimal RENTAL_TIME = BigDecimal.valueOf(1);
    private static final BigDecimal NEW_RENTAL_TIME = BigDecimal.valueOf(2);
    private static final String REG_DATE = "2019-01-26";
    private static final int CLIENT_ID = 2;
    private static final int CAR_ID = 2;
    private static final int NEW_CLIENT_ID = 2;
    private static final int NEW_CAR_ID = 1;
    private static final String NEW_REG_DATE = "2019-02-12";
    private static final String UNEXPECTED_REG_DATE = "2002-02-13";
    private static final int DTO_ORDER_ID = 1;
    private static final String DTO_PASSPORT_NUMBER = "AB42123";
    private static final String DTO_CAR_NUMBER = "BY2312";
    private static final BigDecimal DTO_RENTAL_TIME = BigDecimal.valueOf(2);
    private static final BigDecimal DTO_TOTAL_COST = BigDecimal.valueOf(140).setScale(2, RoundingMode.CEILING);
    private static final Date DTO_REG_DATE = Date.valueOf("2019-01-22");
    private static final RentalOrderDateInterval REG_DATE_INTERVAL1 = new RentalOrderDateInterval
            ("2019-01-18","2019-01-26");
    private static final RentalOrderDateInterval REG_DATE_INTERVAL2 = new RentalOrderDateInterval
            ("2019-02-06","2019-02-09");
    private static final BigDecimal DTO_RENTAL_COST = BigDecimal.valueOf(70).setScale(2, RoundingMode.CEILING);

    @Autowired
    private RentalOrderDao rentalOrderDao;

    @Test
    void shouldFindAllRentalOrders() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        assertNotNull(orders);
        assertTrue(orders.count() > 0);
    }

    @Test
    void shouldFindAllDTOs() {
        Stream<RentalOrderDTO> ordersDTO = rentalOrderDao.findAllDTOs();
        assertNotNull(ordersDTO);
        assertTrue(ordersDTO.count() > 0);
    }

    @Test
    void shouldFindRentalOrderById() {
        RentalOrder order = rentalOrderDao.findById(2).get();
        assertNotNull(order);
        assertEquals(CLIENT_ID, order.getClientId().intValue());
        assertEquals(CAR_ID, order.getCarId().intValue());
        assertEquals(ORDER_ID, order.getOrderId().intValue());
        assertEquals(RENTAL_TIME, order.getRentalTime());
        assertEquals(Date.valueOf(REG_DATE), order.getRegDate());
    }

    @Test
    void shouldFindAllRentalOrdersListCheckCount() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        assertNotNull(orders);
        assertEquals(FULL_RENTAL_ORDER_LIST, orders.count());
    }

    @Test
    void shouldFindDTOById() {
        RentalOrderDTO orderDTO = rentalOrderDao.findDTOById(1).get();
        assertNotNull(orderDTO);
        assertEquals(orderDTO.getOrderId().intValue(), DTO_ORDER_ID);
        assertEquals(orderDTO.getPassportNumber(), DTO_PASSPORT_NUMBER);
        assertEquals(orderDTO.getCarNumber(), DTO_CAR_NUMBER);
        assertEquals(orderDTO.getRegDate(), DTO_REG_DATE);
        assertEquals(orderDTO.getRentalTime(), DTO_RENTAL_TIME);
        assertEquals(orderDTO.getRentalCost(), DTO_RENTAL_COST);
        assertEquals(orderDTO.getTotalCost(), DTO_TOTAL_COST);
    }

    @Test
    void shouldFindDTOsByDate() {
        Stream<RentalOrderDTO> orderDTO1 = rentalOrderDao.findDTOsByDate(REG_DATE_INTERVAL1);
        Stream<RentalOrderDTO> orderDTO2 = rentalOrderDao.findDTOsByDate(REG_DATE_INTERVAL2);
        assertNotNull(orderDTO1);
        assertNotNull(orderDTO2);
        assertEquals(2, orderDTO1.count());
        assertEquals(1, orderDTO2.count());
    }


    @Test
    void shouldAddNewRentalOrder() {
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
    void shouldUpdateRentalOrder() {
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
    void shouldDeleteRentalOrder() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        RentalOrder order = orders.findFirst().get();
        rentalOrderDao.delete(order.getOrderId());

        assertThrows(DataAccessException.class, () -> rentalOrderDao.findById(order.getOrderId()));
    }
}