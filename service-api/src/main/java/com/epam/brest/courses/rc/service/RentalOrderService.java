package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service interface of rental order.
 */
public interface RentalOrderService {

    /**
     * Get all rental orders from DAO.
     *
     * @return rental orders stream.
     */
    Stream<RentalOrder> findAll();

    /**
     * Get all DTO rental orders from DAO.
     *
     * @return DTO rental orders stream.
     */
    Stream<RentalOrderDTO> findAllDTOs();

    /**
     * Get rental order by ID from DAO.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    Optional<RentalOrder> findById(Integer orderId);

    /**
     * Get DTO rental order by ID from DAO.
     *
     * @param orderId DTO rental order ID for getting.
     * @return DTO rental order by ID.
     */
    Optional<RentalOrderDTO> findDTOById(Integer orderId);

    /**
     * Gets DTO rental orders between certain dates from DAO.
     *
     * @param interval date range for compare.
     * @return DTO rental orders stream filtered by date.
     */
    Stream<RentalOrderDTO> findDTOsByDate(RentalOrderDateInterval interval);

    /**
     * Add new rental order to DAO.
     *
     * @param order new rental order.
     * @return new rental order.
     */
    Optional<RentalOrder> add(RentalOrder order);

    /**
     * Update rental order in DAO.
     *
     * @param order rental order for updating.
     */
    void update(RentalOrder order);

    /**
     * Delete rental order with specified ID from DAO.
     *
     * @param orderId rental order ID for delete.
     */
    void delete(int orderId);
}
