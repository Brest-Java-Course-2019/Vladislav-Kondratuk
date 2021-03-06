package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of CarDao. Gets data from database.
 */
public class CarDaoImpl implements CarDao {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    /**
     * Constant fields.
     */
    private static final String CAR_ID = "carId";
    private static final String CAR_DESCRIPTION = "carDescription";
    private static final String CAR_NUMBER = "carNumber";
    private static final String RENTAL_COST = "rentalCost";
    private static final String NUMBER_OF_ORDERS = "numberOfOrders";

    /**
     * SQL Select-all cars String.
     */
    @Value("${car.selectAll}")
    private String getAllCarsSql;

    /**
     * SQL Select-By-Id car String.
     */
    @Value("${car.selectById}")
    private String getCarByIdSql;

    /**
     * SQL Insert new car String.
     */
    @Value("${car.insert}")
    private String addCarSql;

    /**
     * SQL Update car String.
     */
    @Value("${car.update}")
    private String updateCarSql;

    /**
     * SQL Delete-By-Id car String.
     */
    @Value("${car.delete}")
    private String deleteCarSql;

    /**
     * SQL Check count unique car registration number String.
     */
    @Value("${car.checkCountCarNumber}")
    private String checkCountCarNumberSql;

    /**
     * SQL Select-all DTO cars String.
     */
    @Value("${carDTO.selectAll}")
    private String getAllDTOCarsSql;

    /**
     * SQL Select-By-Id DTO car String.
     */
    @Value("${carDTO.selectById}")
    private String getCarDTOByIdSql;

    /**
     * SQL Select-By-Cost DTO car String.
     */
    @Value("${carDTO.setectByCost}")
    private String getCarDTOByCostSql;

    /**
     * Property namedParameterJdbcTemplate.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * method setNamedParameterJdbcTemplate setter method for namedParameterJdbcTemplate property.
     *
     * @param namedParameterJdbcTemplate input value.
     */
    public CarDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Get all cars.
     *
     * @return cars stream.
     */
    @Override
    public Stream<Car> findAll() {
        LOGGER.debug("findAll()");
        List<Car> carList = namedParameterJdbcTemplate.query(getAllCarsSql, new CarDaoRowMapper());
        return carList.stream();
    }

    /**
     * Get all DTO cars.
     *
     * @return DTO cars stream.
     */
    @Override
    public Stream<CarDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        List<CarDTO> carDTOList = namedParameterJdbcTemplate.query(getAllDTOCarsSql, new CarDTORowMapper());
        return carDTOList.stream();
    }

    /**
     * Get car by ID.
     *
     * @param carId car ID for getting.
     * @return car by ID.
     */
    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(CAR_ID, carId);
        Car car = namedParameterJdbcTemplate.queryForObject(getCarByIdSql, mapSqlParameterSource,
                BeanPropertyRowMapper.newInstance(Car.class));
        return Optional.ofNullable(car);
    }

    /**
     * Get DTO car by ID.
     *
     * @param carId DTO car ID for getting.
     * @return DTO car by ID.
     */
    @Override
    public Optional<CarDTO> findDTOById(Integer carId) {
        LOGGER.debug("findDTOById({})", carId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(CAR_ID, carId);
        CarDTO carDTO = namedParameterJdbcTemplate.queryForObject(getCarDTOByIdSql, mapSqlParameterSource,
                BeanPropertyRowMapper.newInstance(CarDTO.class));
        return Optional.ofNullable(carDTO);
    }

    /**
     * Gets DTO cars between certain cost.
     *
     * @param interval cost range for compare.
     * @return DTO cars stream filtered by cost.
     */
    @Override
    public Stream<CarDTO> findDTOsByCost(CarCostInterval interval) {
        LOGGER.debug("findDTOsByDate({})", interval);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("startInterval", interval.getCostStartInterval());
        parameterSource.addValue("endInterval", interval.getCostEndInterval());
        List<CarDTO> carDTOList =
                namedParameterJdbcTemplate
                        .query(getCarDTOByCostSql, parameterSource, new CarDTORowMapper());
        return carDTOList.stream();
    }

    /**
     * Add new car.
     *
     * @param car new car.
     * @return new car.
     */
    @Override
    public Optional<Car> add(Car car) {
        LOGGER.debug("add({})", car);
        return Optional.of(car)
                .filter(this::isCarNumberUnique)
                .map(this::insertCar)
                .orElseThrow(() -> new IllegalArgumentException("Car with the same number already exist in DB."));
    }

    /**
     * Check is new car registration number unique, before insert.
     *
     * @param car new car.
     * @return true if car registration number is unique.
     */
    private boolean isCarNumberUnique(Car car) {
        return namedParameterJdbcTemplate.queryForObject(checkCountCarNumberSql,
                new MapSqlParameterSource(CAR_NUMBER, car.getCarNumber()), Integer.class) == 0;
    }

    /**
     * Insert values for new car.
     *
     * @param car to set values for new car.
     * @return new car.
     */
    private Optional<Car> insertCar(Car car) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CAR_DESCRIPTION, car.getCarDescription());
        mapSqlParameterSource.addValue(CAR_NUMBER, car.getCarNumber());
        mapSqlParameterSource.addValue(RENTAL_COST, car.getRentalCost());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(addCarSql, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        car.setCarId(generatedKeyHolder.getKey().intValue());
        return Optional.of(car);
    }

    /**
     * CarDaoRowMapper - for creating models from resultSet.
     */
    private class CarDaoRowMapper implements RowMapper<Car> {

        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Car car = new Car();
            car.setCarId(resultSet.getInt(CAR_ID));
            car.setCarDescription(resultSet.getString(CAR_DESCRIPTION));
            car.setCarNumber(resultSet.getString(CAR_NUMBER));
            car.setRentalCost(resultSet.getBigDecimal(RENTAL_COST));
            return car;
        }
    }

    /**
     * CarDTORowMapper - for creating models from resultSet.
     */
    private class CarDTORowMapper implements RowMapper<CarDTO> {

        @Override
        public CarDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            CarDTO carDTO = new CarDTO();
            carDTO.setCarId(resultSet.getInt(CAR_ID));
            carDTO.setCarDescription(resultSet.getString(CAR_DESCRIPTION));
            carDTO.setRentalCost(resultSet.getBigDecimal(RENTAL_COST));
            carDTO.setNumberOfOrders(resultSet.getInt(NUMBER_OF_ORDERS));
            return carDTO;
        }
    }

    /**
     * Update car.
     *
     * @param car car for updating.
     */
    @Override
    public void update(Car car) {
        Optional.of(namedParameterJdbcTemplate.update(updateCarSql, new BeanPropertySqlParameterSource(car)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update car in DB"));
    }

    /**
     * Delete car with specified ID.
     *
     * @param carId car ID for delete.
     */
    @Override
    public void delete(int carId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CAR_ID, carId);
        Optional.of(namedParameterJdbcTemplate.update(deleteCarSql, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete car from DB"));
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
