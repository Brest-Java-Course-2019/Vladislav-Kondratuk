package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RentalOrderDaoImpl implements RentalOrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderDaoImpl.class);
    private static final String SELECT_ALL = "select orderId, orderNumber, rentalTime, totalCost from rentalOrder";
    private static final String FIND_BY_ID = "select orderId, orderNumber, rentalTime, totalCost from rentalOrder " +
            "where orderId =:orderID ";
    private static final String RENTAL_ORDER_ID = "orderID";
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String RENTAL_TIME = "rentalTime";
    private static final String TOTAL_COST = "totalCost";


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
        LOGGER.debug("findAll({})", orderId);
        SqlParameterSource namedParameters = new MapSqlParameterSource(RENTAL_ORDER_ID, orderId);
        RentalOrder rentalOrder = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, namedParameters,
                BeanPropertyRowMapper.newInstance(RentalOrder.class));
        return Optional.ofNullable(rentalOrder);
    }

    private class RentalOrderRowMapper implements RowMapper<RentalOrder> {

        @Override
        public RentalOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            RentalOrder rentalOrder = new RentalOrder();
            rentalOrder.setOrderId(resultSet.getInt(RENTAL_ORDER_ID));
            rentalOrder.setOrderNumber(resultSet.getInt(ORDER_NUMBER));
            rentalOrder.setRentalTime(resultSet.getBigDecimal(RENTAL_TIME));
            rentalOrder.setTotalCost(resultSet.getBigDecimal(TOTAL_COST));
            return rentalOrder;
        }
    }
}
