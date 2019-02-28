package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.RentalOrderDao;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.stub.RentalOrderStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Transactional
public class RentalOrderServiceImpl implements RentalOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImpl.class);

    private RentalOrderDao orderDao;

    public RentalOrderServiceImpl(RentalOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Stream<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        return orderDao.findAll();
    }

    @Override
    public Stream<RentalOrderStub> findAllStubs() {
        LOGGER.debug("findAllStubs");
        return orderDao.findAllStubs();
    }

    @Override
    public RentalOrder findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        return orderDao.findById(orderId)
               .orElseThrow(() -> new RuntimeException("Failed to get rental order from DB"));
    }

    @Override
    public RentalOrderStub findStubById(Integer orderId) {
        LOGGER.debug("findStubById({})", orderId);
        return orderDao.findStubById(orderId)
                .orElseThrow(() -> new RuntimeException("Failed to get rental order stub from DB"));
    }

}
