package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO Interface of rental order.
 */
public interface RentalOrderDao {

    /**
     * Get all rental orders from DB.
     *
     * @return rental orders stream.
     */
    Stream<RentalOrder> findAll();

    /**
     * Get all DTO rental orders from DB.
     *
     * @return DTO rental orders stream.
     */
    Stream<RentalOrderDTO> findAllDTOs();

    /**
     * Get rental order by ID from DB.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    RentalOrder findById(Integer orderId);

    /**
     * Get DTO rental order by ID from DB.
     *
     * @param orderId DTO rental order ID for getting.
     * @return DTO rental order by ID.
     */
    RentalOrderDTO findDTOById(Integer orderId);

    /**
     * Gets DTO rental orders between certain dates from DB.
     *
     * @param interval date range for compare.
     * @return DTO rental orders stream filtered by date.
     */
    Stream<RentalOrderDTO> findDTOsByDate(RentalOrderDateInterval interval);

    /**
     * Add new rental order to DB.
     *
     * @param order new rental order.
     * @return new rental order.
     */
    Optional<RentalOrder> add(RentalOrder order);

    /**
     * Update rental order in DB.
     *
     * @param order rental order for updating.
     */
    void update(RentalOrder order);

    /**
     * Delete rental order with specified ID from DB.
     *
     * @param orderId rental order ID for delete.
     */
    void delete(int orderId);
}
