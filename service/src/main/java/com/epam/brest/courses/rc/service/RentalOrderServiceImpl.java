package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.RentalOrderDao;
import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of RentalOrderService. Gets data from dao and database.
 */
@Transactional
public class RentalOrderServiceImpl implements RentalOrderService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImpl.class);

    /**
     * DAO.
     */
    private RentalOrderDao dao;

    /**
     * Service constructor.
     * @param dao dao of rental order objects.
     */
    public RentalOrderServiceImpl(RentalOrderDao dao) {
        this.dao = dao;
    }

    /**
     * Method findAll gets all rental orders.
     *
     * @return list of all rental orders.
     */
    @Override
    public List<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll().collect(Collectors.toList());
    }

    /**
     * Method findAllDTOs gets all rental orders DTO.
     *
     * @return list of all rental orders DTO.
     */
    @Override
    public List<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs().collect(Collectors.toList());
    }

    /**
     * Method findById get rental order by ID.
     *
     * @param orderId rental order ID for getting.
     * @return order by ID.
     */
    @Override
    public RentalOrder findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        return dao.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Failed to get rental order from DB"));
    }

    /**
     * Method findById get rental order DTO by ID.
     *
     * @param orderId DTO rental order ID for getting.
     * @return order DTO by ID.
     */
    @Override
    public RentalOrderDTO findDTOById(Integer orderId) {
        LOGGER.debug("findDTOById({})", orderId);
        return dao.findDTOById(orderId)
                .orElseThrow(() -> new RuntimeException("Failed to get rental order dto from DB"));
    }

    /**
     * Method findDTOsByDate get rental order by Date interval
     *
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO rental orders list filtered by date.
     */
    @Override
    public List<RentalOrderDTO> findDTOsByDate(final String startDate, final String endDate) {
        LOGGER.debug("findDTOsByDate({}, {})", startDate, endDate);
        return dao.findDTOsByDate(
                new RentalOrderDateInterval(startDate, endDate)).collect(Collectors.toList());
    }

    /**
     * Method add new rental order.
     *
     * @param order new rental order..
     */
    @Override
    public void add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        dao.add(order);
    }

    /**
     * Method update rental order.
     *
     * @param order rental order for updating.
     */
    @Override
    public void update(RentalOrder order) {
        LOGGER.debug("update({})", order);
        dao.update(order);
    }

    /**
     * Method delete rental order by ID.
     *
     * @param orderId rental order ID for delete.
     */
    @Override
    public void delete(int orderId) {
        LOGGER.debug("delete({})", orderId);
        dao.delete(orderId);
    }
}
