package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.RentalOrderDao;
import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

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
     * @return stream of all rental orders.
     */
    @Override
    public Stream<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll();
    }

    /**
     * Method findAllDTOs gets all rental orders DTO.
     *
     * @return stream of all rental orders DTO.
     */
    @Override
    public Stream<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs();
    }

    /**
     * Method findById get rental order by ID.
     *
     * @param orderId rental order ID for getting.
     * @return order by ID.
     */
    @Override
    public Optional<RentalOrder> findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        return dao.findById(orderId);
    }

    /**
     * Method findById get rental order DTO by ID.
     *
     * @param orderId DTO rental order ID for getting.
     * @return order DTO by ID.
     */
    @Override
    public Optional<RentalOrderDTO> findDTOById(Integer orderId) {
        LOGGER.debug("findDTOById({})", orderId);
        return dao.findDTOById(orderId);
    }

    /**
     * Method findDTOsByDate get rental order by Date interval.
     *
     * @param interval date range for compare.
     * @return order by Date interval.
     */
    @Override
    public Stream<RentalOrderDTO> findDTOsByDate(RentalOrderDateInterval interval) {
        LOGGER.debug("findDTOsByDate({})", interval);
        return dao.findDTOsByDate(interval);
    }

    /**
     * Method add new rental order.
     *
     * @param order new rental order.
     * @return new order.
     */
    @Override
    public Optional<RentalOrder> add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        return dao.add(order);
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
