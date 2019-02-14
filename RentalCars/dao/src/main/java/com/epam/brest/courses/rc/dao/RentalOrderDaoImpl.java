package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Optional;
import java.util.stream.Stream;

public class RentalOrderDaoImpl implements RentalOrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderDaoImpl.class);

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RentalOrderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        return null;
    }

    @Override
    public Optional<RentalOrder> findById(Integer orderId) {
        return Optional.empty();
    }
}
