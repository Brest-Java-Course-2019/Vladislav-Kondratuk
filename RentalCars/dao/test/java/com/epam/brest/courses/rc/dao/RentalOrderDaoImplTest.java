package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
class RentalOrderDaoImplTest {

    @Autowired
    private RentalOrderDao rentalOrderDao;

    @Test
    void findAll() {
        Stream<RentalOrder> orders = rentalOrderDao.findAll();
        assertNotNull(orders);
    }
}