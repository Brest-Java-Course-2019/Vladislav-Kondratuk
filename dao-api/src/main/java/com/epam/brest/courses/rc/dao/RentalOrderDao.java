package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO Interface of rental order.
 */
public interface RentalOrderDao {

    /**
     * Get all rental orders.
     *
     * @return rental orders stream.
     */
    Stream<RentalOrder> findAll();

    /**
     * Get rental order by ID.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    Optional<RentalOrder> findById(Integer orderId);

    /**
     * Add new rental order.
     *
     * @param order new rental order.
     * @return new rental order.
     */
    Optional<RentalOrder> add(RentalOrder order);

    /**
     * Update rental order.
     *
     * @param order rental order for updating.
     */
    void update(RentalOrder order);

    /**
     * Delete rental order with specified ID.
     *
     * @param orderId rental order ID for delete.
     */
    void delete(int orderId);
}
