package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of RentalOrderDao. Gets data from database.
 */
public class RentalOrderDaoImpl implements RentalOrderDao {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderDaoImpl.class);

    /**
     * Constant fields.
     */
    private static final String RENTAL_TIME = "rentalTime";
    private static final String ORDER_ID = "orderId";
    private static final String CLIENT_ID = "clientId";
    private static final String CAR_ID = "carId";
    private static final String REG_DATE = "regDate";

    /**
     * SQL Select-all rental orders String.
     */
    @Value("${rentalOrder.selectAll}")
    private String getAllOrdersSql;

    /**
     * SQL Select-By-Id rental order String.
     */
    @Value("${rentalOrder.selectById}")
    private String getOrderByIdSql;

    /**
     * SQL Insert new rental order String.
     */
    @Value("${rentalOrder.insert}")
    private String addOrderSql;

    /**
     * SQL Update rental order String.
     */
    @Value("${rentalOrder.update}")
    private String updateOrderSql;

    /**
     * SQL Delete-By-Id rental order String.
     */
    @Value("${rentalOrder.delete}")
    private String deleteOrderSql;

    /**
     * Named parameter JDBC template.
     */
    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RentalOrderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Get all rental orders.
     *
     * @return rental orders stream.
     */
    @Override
    public Stream<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        List<RentalOrder> rentalOrderList = namedParameterJdbcTemplate.query(getAllOrdersSql, new RentalOrderRowMapper());
        return rentalOrderList.stream();
    }

    /**
     * Get rental order by ID.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    @Override
    public Optional<RentalOrder> findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(ORDER_ID, orderId);
        RentalOrder rentalOrder = namedParameterJdbcTemplate.queryForObject(getOrderByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(RentalOrder.class));
        return Optional.ofNullable(rentalOrder);
    }

    /**
     * Add new rental order.
     *
     * @param order new rental order.
     * @return new rental order.
     */
    @Override
    public Optional<RentalOrder> add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        return Optional.of(order)
                .map(this::insertRentalOrder)
                .orElseThrow(() -> new IllegalArgumentException("RentalOrder with the same id already exist in DB."));
    }

    /**
     * Insert values for new rental order.
     *
     * @param order to set values for new rental order.
     * @return new rental order.
     */
    private Optional<RentalOrder> insertRentalOrder(RentalOrder order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CLIENT_ID, order.getClientId());
        mapSqlParameterSource.addValue(CAR_ID, order.getCarId());
        mapSqlParameterSource.addValue(RENTAL_TIME, order.getRentalTime());
        mapSqlParameterSource.addValue(REG_DATE, order.getRegDate());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(addOrderSql, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        order.setOrderId(generatedKeyHolder.getKey().intValue());
        return Optional.of(order);
    }

    /**
     * RentalOrderRowMapper - for creating models from resultSet.
     */
    private class RentalOrderRowMapper implements RowMapper<RentalOrder> {

        @Override
        public RentalOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            RentalOrder rentalOrder = new RentalOrder();
            rentalOrder.setOrderId(resultSet.getInt(ORDER_ID));
            rentalOrder.setClientId(resultSet.getInt(CLIENT_ID));
            rentalOrder.setCarId(resultSet.getInt(CAR_ID));
            rentalOrder.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            rentalOrder.setRegDate(resultSet.getDate(REG_DATE));
            return rentalOrder;
        }
    }

    /**
     * Update rental order.
     *
     * @param order rental order for updating.
     */
    @Override
    public void update(RentalOrder order) {
        Optional.of(namedParameterJdbcTemplate.update(updateOrderSql, new BeanPropertySqlParameterSource(order)))
                .orElseThrow(() -> new RuntimeException("Failed to update rentalOrder in DB"));
    }

    /**
     * Delete rental order with specified ID.
     *
     * @param orderId rental order ID for delete.
     */
    @Override
    public void delete(int orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_ID, orderId);
        Optional.of(namedParameterJdbcTemplate.update(deleteOrderSql, mapSqlParameterSource))
                .orElseThrow(() -> new RuntimeException("Failed to delete rentalOrder from DB"));
    }

}
