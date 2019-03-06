package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.date.RegDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.stub.RentalOrderStub;
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
    private static final String SELECT_ALL = "select orderId, clientId, carId, rentalTime, regDate from rentalOrder";
    private static final String FIND_BY_ID = "select orderId, clientId, carId, rentalTime, regDate from rentalOrder " +
            "where orderId =:orderId ";
    private static final String CHECK_COUNT_ORDER_ID = "select count(orderId) from rentalOrder where orderId " +
            "= :orderId";
    private static final String INSERT = "insert into rentalOrder (clientId, carId, rentalTime, regDate) " +
            "values (:clientId, :carId, :rentalTime, :regDate)";
    private static final String UPDATE = "update rentalOrder set clientId = :clientId, carId = :carId, rentalTime = :rentalTime, regDate = :regDate where orderId = :orderId";
    private static final String DELETE = "delete from rentalOrder where orderId = :orderId";
    private static final String RENTAL_TIME = "rentalTime";
    private static final String ORDER_ID = "orderId";
    private static final String CLIENT_ID = "clientId";
    private static final String CAR_ID = "carId";
    private static final String REG_DATE = "regDate";
    private static final String SELECT_ALL_STUBS = "SELECT r.orderId, r.rentalTime, cr.carNumber, cr.rentalCost, cl.passportNumber, " +
            "(r.rentalTime * cr.rentalCost) as totalCost, r.regDate FROM rentalOrder r LEFT JOIN car cr ON (cr.carId = r.carId) " +
            "LEFT JOIN client cl ON (cl.clientId = r.clientId) GROUP BY r.orderId ";
    private static final String PASSPORT_NUMBER = "passportNumber";
    private static final String CAR_NUMBER = "carNumber";
    private static final String TOTAL_COST = "totalCost";
    private static final String SELECT_STUB_BY_ID = "SELECT r.orderId, r.rentalTime, cr.carNumber, cr.rentalCost, cl.passportNumber, " +
            "(r.rentalTime * cr.rentalCost) as totalCost, r.regDate FROM rentalOrder r LEFT JOIN car cr ON (cr.carId = r.carId) " +
            "LEFT JOIN client cl ON (cl.clientId = r.clientId) WHERE orderId = :orderId GROUP BY r.orderId ";
    private static final String SELECT_ALL_STUBS_BY_DATE = "SELECT r.orderId, r.rentalTime, cr.carNumber, cr.rentalCost, cl.passportNumber, " +
            "(r.rentalTime * cr.rentalCost) as totalCost, r.regDate FROM rentalOrder r LEFT JOIN car cr ON (cr.carId = r.carId) " +
            "LEFT JOIN client cl ON (cl.clientId = r.clientId) WHERE r.regDate >= :startInterval AND r.regDate <= :endInterval GROUP BY r.orderId ";

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
    public Stream<RentalOrderStub> findAllStubs() {
        LOGGER.debug("findAllStubs()");
        List<RentalOrderStub> rentalOrderList =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_STUBS,
                                (resultSet, i) -> new RentalOrderStub()
                                        .orderId(resultSet.getInt(ORDER_ID))
                                        .passportNumber(resultSet.getString(PASSPORT_NUMBER))
                                        .carNumber(resultSet.getString(CAR_NUMBER))
                                        .rentalTime(resultSet.getBigDecimal(RENTAL_TIME))
                                        .totalCost(resultSet.getBigDecimal(TOTAL_COST))
                                        .regDate(resultSet.getDate(REG_DATE)));
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
    public Optional<RentalOrderStub> findStubById(Integer orderId) {
        LOGGER.debug("findStubById({})", orderId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ORDER_ID, orderId);
        RentalOrderStub orderStub = namedParameterJdbcTemplate.queryForObject(SELECT_STUB_BY_ID, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(RentalOrderStub.class));
        return Optional.ofNullable(orderStub);
    }

    @Override
    public Optional<RentalOrder> add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        return Optional.of(order)
                .filter(this::isOrderRegDate)
                .map(this::insertRentalOrder)
                .orElseThrow(() -> new IllegalArgumentException("RentalOrder with the same id already exist in DB."));
    }

    @Override
    public Stream<RentalOrderStub> findStubByDate(RegDateInterval interval) {
        LOGGER.debug("findStubByDate({}, {})", interval);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("startInterval", interval.getRegStartInterval());
        parameterSource.addValue("endInterval", interval.getRegEndInterval());
        List<RentalOrderStub> rentalOrderList =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_STUBS_BY_DATE, parameterSource, new RentalOrderStubRowMapper());
        return rentalOrderList.stream();
    }

    private boolean isOrderRegDate(RentalOrder order) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_ORDER_ID,
                new MapSqlParameterSource(ORDER_ID, order.getOrderId()),
                Integer.class) == 0;
    }

    private Optional<RentalOrder> insertRentalOrder(RentalOrder order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CLIENT_ID, order.getClientId());
        mapSqlParameterSource.addValue(CAR_ID, order.getCarId());
        mapSqlParameterSource.addValue(RENTAL_TIME, order.getRentalTime());
        mapSqlParameterSource.addValue(REG_DATE, order.getRegDate());

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
            rentalOrder.setClientId(resultSet.getInt(CLIENT_ID));
            rentalOrder.setCarId(resultSet.getInt(CAR_ID));
            rentalOrder.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            rentalOrder.setRegDate(resultSet.getDate(REG_DATE));
            return rentalOrder;
        }
    }

    private class RentalOrderStubRowMapper implements RowMapper<RentalOrderStub>{

        @Override
        public RentalOrderStub mapRow(ResultSet resultSet, int i) throws SQLException {
            RentalOrderStub orderStub = new RentalOrderStub();
            orderStub.setOrderId(resultSet.getInt(ORDER_ID));
            orderStub.setPassportNumber(resultSet.getString(PASSPORT_NUMBER));
            orderStub.setCarNumber(resultSet.getString(CAR_NUMBER));
            orderStub.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            orderStub.setTotalCost(resultSet.getBigDecimal(TOTAL_COST));
            orderStub.setRegDate(resultSet.getDate(REG_DATE));
            return orderStub;
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


