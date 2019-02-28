package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.stub.RentalOrderStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath:test-service.xml"})
class RentalOrderServiceImplTest {

    @Autowired
    private RentalOrderService orderService;

    @Test
    void findAll() {
        Stream<RentalOrder> orders = orderService.findAll();
        assertNotNull(orders);
        assertTrue(orders.count() > 0);
    }

    @Test
    void findAllStubs() {
        Stream<RentalOrderStub> orderStub = orderService.findAllStubs();
        assertNotNull(orderStub);
        assertTrue(orderStub.count() > 0);
    }

    @Test
    void findById() {
        Optional<RentalOrder> firstOrder = orderService.findAll().findFirst();
        assertTrue(firstOrder.isPresent());
        Integer id = firstOrder.get().getOrderId();

        RentalOrder order = orderService.findById(id);
        assertNotNull(order);

        assertEquals(firstOrder.get().getRentalTime(), order.getRentalTime());
    }

    @Test
    void findStubById() {
        Optional<RentalOrderStub> firstOrderStub = orderService.findAllStubs().findFirst();
        assertTrue(firstOrderStub.isPresent());
        Integer id = firstOrderStub.get().getOrderId();

        RentalOrderStub orderStub = orderService.findStubById(id);
        assertNotNull(orderStub);

        assertEquals(firstOrderStub.get().getOrderId(), orderStub.getOrderId());
    }

}