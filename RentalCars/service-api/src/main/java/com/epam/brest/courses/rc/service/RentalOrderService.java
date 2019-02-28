package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.stub.RentalOrderStub;

import java.util.stream.Stream;

public interface RentalOrderService {

    Stream<RentalOrder> findAll();

    Stream<RentalOrderStub> findAllStubs();

    RentalOrder findById(Integer orderId);

    RentalOrderStub findStubById(Integer orderId);

}
