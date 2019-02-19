package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;

import java.util.Optional;
import java.util.stream.Stream;

public interface RentalOrderDao {

    Stream<RentalOrder> findAll();

    Optional<RentalOrder> findById(Integer orderId);

    Optional<RentalOrder> add(RentalOrder order);

    void update(RentalOrder order);

    void delete(int orderId);
}