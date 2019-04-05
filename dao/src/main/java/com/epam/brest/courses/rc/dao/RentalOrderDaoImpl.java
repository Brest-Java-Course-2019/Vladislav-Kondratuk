package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
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
    private static final String CAR_NUMBER = "carNumber";
    private static final String PASSPORT_NUMBER = "passportNumber";
    private static final String RENTAL_COST = "rentalCost";
    private static final String TOTAL_COST = "totalCost";
    private static final String START_INTERVAL = "startInterval";
    private static final String END_INTERVAL = "endInterval";

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
     * SQL Select-all DTO rental orders String.
     */
    @Value("${rentalOrderDTO.selectAll}")
    private String getAllOrdersDTOSql;

    /**
     * SQL Select-By-Id DTO rental order String.
     */
    @Value("${rentalOrderDTO.selectById}")
    private String getOrderDTOByIdSql;

    /**
     * SQL Select-By-Date DTO rental order String.
     */
    @Value("${rentalOrderDTO.selectByDate}")
    private String getOrderDTOByDateSql;

    /**
     * Property namedParameterJdbcTemplate.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * method setNamedParameterJdbcTemplate setter method for namedParameterJdbcTemplate property.
     *
     * @param namedParameterJdbcTemplate input value.
     */
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
        List<RentalOrder> rentalOrderList = namedParameterJdbcTemplate
                .query(getAllOrdersSql, new RentalOrderRowMapper());
        return rentalOrderList.stream();
    }

    /**
     * Get all DTO rental orders
     *
     * @return DTO rental orders stream.
     */
    @Override
    public Stream<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        List<RentalOrderDTO> rentalOrderList = namedParameterJdbcTemplate
                .query(getAllOrdersDTOSql, new RentalOrderDTORowMapper());
        return rentalOrderList.stream();
    }

    /**
     * Get rental order by ID.
     *
     * @param orderId rental order ID for getting.
     * @return rental order by ID.
     */
    @Override
    public RentalOrder findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(ORDER_ID, orderId);
        return namedParameterJdbcTemplate.queryForObject(getOrderByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(RentalOrder.class));
    }

    /**
     * Get DTO rental order by ID.
     *
     * @param orderId DTO rental order ID for getting.
     * @return DTO rental order by ID.
     */
    @Override
    public RentalOrderDTO findDTOById(Integer orderId) {
        LOGGER.debug("findDTOById({})", orderId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(ORDER_ID, orderId);
        return namedParameterJdbcTemplate.queryForObject(getOrderDTOByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(RentalOrderDTO.class));
    }

    /**
     * Gets DTO rental orders between certain dates.
     *
     * @param interval date range for compare.
     * @return DTO rental orders stream filtered by date.
     */
    @Override
    public Stream<RentalOrderDTO> findDTOsByDate(RentalOrderDateInterval interval) {
        LOGGER.debug("findDTOsByDate({})", interval);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(START_INTERVAL, interval.getRegStartInterval());
        parameterSource.addValue(END_INTERVAL, interval.getRegEndInterval());
        List<RentalOrderDTO> rentalOrderList =
                namedParameterJdbcTemplate
                        .query(getOrderDTOByDateSql, parameterSource, new RentalOrderDTORowMapper());
        return rentalOrderList.stream();
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
                .orElseThrow(() -> new IllegalArgumentException("Fail to add RentalOrder into DB."));
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
     * RentalOrderDTORowMapper - for creating models from resultSet.
     */
    private class RentalOrderDTORowMapper implements RowMapper<RentalOrderDTO> {

        @Override
        public RentalOrderDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            RentalOrderDTO rentalOrderDTO = new RentalOrderDTO();
            rentalOrderDTO.setOrderId(resultSet.getInt(ORDER_ID));
            rentalOrderDTO.setPassportNumber(resultSet.getString(PASSPORT_NUMBER));
            rentalOrderDTO.setCarNumber(resultSet.getString(CAR_NUMBER));
            rentalOrderDTO.setRegDate(resultSet.getDate(REG_DATE));
            rentalOrderDTO.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            rentalOrderDTO.setRentalCost(resultSet.getBigDecimal(RENTAL_COST));
            rentalOrderDTO.setTotalCost(resultSet.getBigDecimal(TOTAL_COST));
            return rentalOrderDTO;
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
                .filter(this::successfullyUpdated)
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
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete rentalOrder from DB"));
    }

    /**
     * Check how many rows is updated.
     *
     * @param numRowsUpdated number of updated rows.
     * @return true if was updated only one row.
     */
    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated == 1;
    }
}
