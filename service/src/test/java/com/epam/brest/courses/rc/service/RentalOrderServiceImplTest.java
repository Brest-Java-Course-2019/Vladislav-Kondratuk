package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.RentalOrderDao;
import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RentalOrderServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImplTest.class);

    private static final Integer ORDER_ID = 1;
    private static final int ONCE = 1;
    private static final int EXPECTED_NUMBER_OF_ORDERS = 1;
    private static final int CAR_ID = 1;
    private static final int CLIENT_ID = 1;
    private static final BigDecimal RENTAL_TIME = BigDecimal.valueOf(1);
    private static final Date REG_DATE = Date.valueOf("2001-01-02");
    private static final int ORDER_ID_DTO = 1;
    private static final String PASSPORT_NUMBER_DTO = "AB42123";
    private static final String CAR_NUMBER_DTO = "BY2312";
    private static final Date REG_DATE_DTO = Date.valueOf("2019-01-22");
    private static final BigDecimal RENTAL_TIME_DTO = BigDecimal.valueOf(2);
    private static final BigDecimal RENTAL_COST_DTO = BigDecimal.valueOf(70);
    private static final BigDecimal TOTAL_COST_DTO = BigDecimal.valueOf(140);
    private static final String START_DATE = "2019-01-18";
    private static final String END_DATE = "2019-01-26";

    private RentalOrderService service;

    private RentalOrderDao dao;

    private RentalOrder rentalOrder = new RentalOrder();

    private RentalOrderDTO rentalOrderDTO = new RentalOrderDTO();

    private RentalOrder createOrder() {
        rentalOrder.setOrderId(ORDER_ID);
        rentalOrder.setClientId(CLIENT_ID);
        rentalOrder.setCarId(CAR_ID);
        rentalOrder.setRentalTime(RENTAL_TIME);
        rentalOrder.setRegDate(REG_DATE);
        return rentalOrder;
    }

    private RentalOrderDTO createOrderDTO() {
        rentalOrderDTO.setOrderId(ORDER_ID_DTO);
        rentalOrderDTO.setPassportNumber(PASSPORT_NUMBER_DTO);
        rentalOrderDTO.setCarNumber(CAR_NUMBER_DTO);
        rentalOrderDTO.setRegDate(REG_DATE_DTO);
        rentalOrderDTO.setRentalTime(RENTAL_TIME_DTO);
        rentalOrderDTO.setRentalCost(RENTAL_COST_DTO);
        rentalOrderDTO.setTotalCost(TOTAL_COST_DTO);
        return rentalOrderDTO;
    }

    @BeforeEach
    void setup() {
        dao = Mockito.mock(RentalOrderDao.class);
        service = new RentalOrderServiceImpl(dao);
    }

    @AfterEach
    void verifyAndReset() {
        Mockito.verifyNoMoreInteractions(dao);
        Mockito.reset(dao);
    }

    @Test
    void shouldFindAllRentalOrders() {
        LOGGER.debug("run test shouldFindAllRentalOrders()");

        Mockito.when(dao.findAll()).thenReturn(Stream.of(createOrder()));

        List<RentalOrder> orderList = service.findAll();
        assertEquals(EXPECTED_NUMBER_OF_ORDERS, orderList.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllDTOs() {
        LOGGER.debug("run test shouldFindAllDTOs()");

        Mockito.when(dao.findAllDTOs()).thenReturn(Stream.of(createOrderDTO()));

        List<RentalOrderDTO> dtoList = service.findAllDTOs();
        assertEquals(EXPECTED_NUMBER_OF_ORDERS, dtoList.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindRentalOrderById() {
        LOGGER.debug("run test shouldFindRentalOrderById()");

        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(createOrder());

        RentalOrder rentalOrder = service.findById(ORDER_ID);
        assertEquals(rentalOrder, createOrder());

        Mockito.verify(dao, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOById() {
        LOGGER.debug("run test shouldFindDTOById()");

        Mockito.when(dao.findDTOById(Mockito.anyInt())).thenReturn(createOrderDTO());

        RentalOrderDTO rentalOrderDTO = service.findDTOById(ORDER_ID_DTO);
        assertEquals(rentalOrderDTO, createOrderDTO());

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOsByDate() {
        LOGGER.debug("run test shouldFindDTOsByDate()");

        Mockito.when(dao.findDTOsByDate(Mockito.any(RentalOrderDateInterval.class)))
                .thenReturn(Stream.of(createOrderDTO()));

        List<RentalOrderDTO> rentalOrderDTO = service.findDTOsByDate(START_DATE, END_DATE);
        assertNotNull(rentalOrderDTO);

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOsByDate(Mockito.any(RentalOrderDateInterval.class));
    }

    @Test
    void shouldAddNewRentalOrder() {
        LOGGER.debug("run test shouldAddNewRentalOrder()");

        Mockito.when(dao.add(Mockito.any(RentalOrder.class))).thenReturn(Optional.of(createOrder()));
        service.add(createOrder());
        Mockito.verify(dao, Mockito.times(ONCE)).add(createOrder());
    }

    @Test
    void shouldUpdateRentalOrder() {
        LOGGER.debug("run test shouldUpdateRentalOrder()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).update(Mockito.any(RentalOrder.class));
        service.update(createOrder());
        Mockito.verify(dao, Mockito.times(ONCE)).update(createOrder());
    }

    @Test
    void shouldDeleteRentalOrder() {
        LOGGER.debug("run test shouldDeleteRentalOrder()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).delete(ORDER_ID);
        Mockito.when(dao.findById(ORDER_ID)).thenReturn(createOrder());
        service.delete(ORDER_ID);
        Mockito.verify(dao,Mockito.times(ONCE)).delete(ORDER_ID);
    }
}