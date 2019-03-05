package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.stub.CarStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class CarDaoImpl implements CarDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDaoImpl.class);
    private static final String CAR_ID = "carId";
    private static final String CAR_DESCRIPTION = "carDescription";
    private static final String CAR_NUMBER = "carNumber";
    private static final String RENTAL_COST = "rentalCost";
    private static final String SELECT_ALL = "select carId, carDescription, carNumber, rentalCost from car";
    private static final String FIND_BY_ID = "select carId, carDescription, carNumber, rentalCost from car " +
            "where carId = :carId";
    private static final String CHECK_COUNT_CAR_NUMBER = "select count(carId) from car where carNumber = :carNumber";
    private static final String INSERT = "insert into car (carDescription, carNumber, rentalCost) " +
            "values (:carDescription, :carNumber, :rentalCost) ";
    private static final String UPDATE = "update car set carDescription = :carDescription, carNumber = :carNumber," +
            "rentalCost = :rentalCost where carId = :carId";
    private static final String DELETE = "delete from car where carId = :carId";
    private static final String SELECT_ALL_STUBS = "select carId, carDescription, rentalCost from car group by carId";
    private static final String FIND_STUB_BY_ID = "select carId, carDescription, rentalCost from car " +
            "where carId = :carId group by carId";
    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<Car> findAll() {
        LOGGER.debug("findAll()");
        List<Car> carList = namedParameterJdbcTemplate.query(SELECT_ALL, new CarDaoRowMapper());
        return carList.stream();
    }

    @Override
    public Stream<CarStub> findAllStubs() {
        LOGGER.debug("findAllStubs()");
        List<CarStub> carStubs = namedParameterJdbcTemplate.query(SELECT_ALL_STUBS, new CarDaoStubRowMapper());
        return carStubs.stream();
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("findById({})", carId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(CAR_ID, carId);
        Car car = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource,
                BeanPropertyRowMapper.newInstance(Car.class));
        return Optional.ofNullable(car);
    }

    @Override
    public Optional<CarStub> findStubById(Integer carId) {
        LOGGER.debug("findStubById({})", carId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(CAR_ID, carId);
        CarStub carStub = namedParameterJdbcTemplate.queryForObject(FIND_STUB_BY_ID, mapSqlParameterSource,
                BeanPropertyRowMapper.newInstance(CarStub.class));
        return Optional.ofNullable(carStub);
    }

    @Override
    public Optional<Car> add(Car car) {
        LOGGER.debug("add({})", car);
        return Optional.of(car)
                .filter(this::isCarNumberUnique)
                .map(this::insertCar)
                .orElseThrow(() -> new IllegalArgumentException("Car with the same number already exist in DB."));
    }

    private boolean isCarNumberUnique(Car car) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_CAR_NUMBER,
                new MapSqlParameterSource(CAR_NUMBER, car.getCarNumber()), Integer.class) == 0;
    }

    private Optional<Car> insertCar(Car car) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CAR_DESCRIPTION, car.getCarDescription());
        mapSqlParameterSource.addValue(CAR_NUMBER, car.getCarNumber());
        mapSqlParameterSource.addValue(RENTAL_COST, car.getRentalCost());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        car.setCarId(generatedKeyHolder.getKey().intValue());
        return Optional.of(car);
    }

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

    private class CarDaoStubRowMapper implements RowMapper<CarStub> {

        @Override
        public CarStub mapRow(ResultSet resultSet, int i) throws SQLException {
            CarStub carStub = new CarStub();
            carStub.setCarId(resultSet.getInt(CAR_ID));
            carStub.setCarDescription(resultSet.getString(CAR_DESCRIPTION));
            carStub.setRentalCost(resultSet.getBigDecimal(RENTAL_COST));
            return carStub;
        }
    }

    @Override
    public void update(Car car) {
        Optional.of(namedParameterJdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(car)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update car in DB"));

    }

    @Override
    public void delete(int carId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CAR_ID, carId);
        Optional.of(namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete car from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }
}
