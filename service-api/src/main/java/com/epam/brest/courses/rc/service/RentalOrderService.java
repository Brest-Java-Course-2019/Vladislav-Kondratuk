package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.model.RentalOrder;

import java.util.List;

/**
 * Service interface of rental order.
 */
public interface RentalOrderService {

    /**
     * Get all rental orders from DAO.
     *
     * @return rental orders list.
     */
    List<RentalOrder> findAll();

    /**
     * Get all DTO rental orders from DAO.
     *
     * @return DTO rental orders list.
     */
    List<RentalOrderDTO> findAllDTOs();

    /**
     * Get rental order by ID from DAO.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    RentalOrder findById(Integer orderId);

    /**
     * Get DTO rental order by ID from DAO.
     *
     * @param orderId DTO rental order ID for getting.
     * @return DTO rental order by ID.
     */
    RentalOrderDTO findDTOById(Integer orderId);

    /**
     * Gets DTO rental orders between certain dates from DAO.
     *
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO rental orders filtered by date.
     */
    List<RentalOrderDTO> findDTOsByDate(final String startDate, final String endDate);

    /**
     * Add new rental order to DAO.
     *
     * @param order new rental order.
     */
     void add(RentalOrder order);

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
