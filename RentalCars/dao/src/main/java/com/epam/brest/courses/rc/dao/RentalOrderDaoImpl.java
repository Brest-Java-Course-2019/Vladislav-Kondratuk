package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class RentalOrderDaoImpl implements RentalOrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderDaoImpl.class);
    private static final String SELECT_ALL = "select orderId, orderNumber, rentalTime, totalCost from rentalOrder";
    private static final String FIND_BY_ID = "select orderId, orderNumber, rentalTime, totalCost from rentalOrder " +
            "where orderId =:orderId ";
    private static final String CHECK_COUNT_ORDER_NUMBER = "select count(orderId) from rentalOrder where orderNumber " +
            "= :orderNumber";
    private static final String INSERT = "insert into rentalOrder (orderNumber, rentalTime, totalCost) " +
            "values (:orderNumber, :rentalTime, :totalCost)";
    private static final String UPDATE = "update rentalOrder set orderNumber = :orderNumber," +
            " rentalTime = :rentalTime, totalCost = :totalCost  where orderId = :orderId";
    private static final String DELETE = "delete from rentalOrder where orderId = :orderId";
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String RENTAL_TIME = "rentalTime";
    private static final String TOTAL_COST = "totalCost";
    private static final String ORDER_ID = "orderId";

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RentalOrderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        List<RentalOrder> rentalOrderList = namedParameterJdbcTemplate.query(SELECT_ALL, new RentalOrderRowMapper());
        return rentalOrderList.stream();
    }

    @Override
    public Optional<RentalOrder> findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(ORDER_ID, orderId);
        RentalOrder rentalOrder = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, namedParameters,
                BeanPropertyRowMapper.newInstance(RentalOrder.class));
        return Optional.ofNullable(rentalOrder);
    }

    @Override
    public Optional<RentalOrder> add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        return Optional.of(order)
                .filter(this::isOrderNumberUnique)
                .map(this::insertRentalOrder)
                .orElseThrow(() -> new IllegalArgumentException("RentalOrder with the same number already exist in DB."));
    }

    private boolean isOrderNumberUnique(RentalOrder order) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_ORDER_NUMBER,
                new MapSqlParameterSource(ORDER_NUMBER, order.getOrderNumber()),
                Integer.class) == 0;
    }

    private Optional<RentalOrder> insertRentalOrder(RentalOrder order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_NUMBER, order.getOrderNumber());
        mapSqlParameterSource.addValue(RENTAL_TIME, order.getRentalTime());
        mapSqlParameterSource.addValue(TOTAL_COST, order.getTotalCost());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        order.setOrderId(generatedKeyHolder.getKey().intValue());
        return Optional.of(order);
    }

    private class RentalOrderRowMapper implements RowMapper<RentalOrder> {

        @Override
        public RentalOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            RentalOrder rentalOrder = new RentalOrder();
            rentalOrder.setOrderId(resultSet.getInt(ORDER_ID));
            rentalOrder.setOrderNumber(resultSet.getInt(ORDER_NUMBER));
            rentalOrder.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            rentalOrder.setTotalCost(resultSet.getBigDecimal(TOTAL_COST));
            return rentalOrder;
        }
    }

    @Override
    public void update(RentalOrder order) {
        Optional.of(namedParameterJdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(order)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update rentalOrder in DB"));
    }

    @Override
    public void delete(int orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_ID, orderId);
        Optional.of(namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete rentalOrder from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }
}


