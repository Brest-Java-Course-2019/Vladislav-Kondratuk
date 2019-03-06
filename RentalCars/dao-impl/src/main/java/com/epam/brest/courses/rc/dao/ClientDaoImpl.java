package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.date.AddDateInterval;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.stub.ClientStub;
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

public class ClientDaoImpl implements ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);
    private static final String SELECT_ALL = "select clientId, passportNumber, firstName, lastName, addDate " +
            "from client";
    private static final String FIND_BY_ID = "select clientId, passportNumber, firstName, lastName, addDate " +
            "from client where clientId = :clientId";
    private static final String CHECK_COUNT_PASSPORT_NUMBER ="select count(clientId) from client " +
            "where passportNumber = :passportNumber";
    private static final String UPDATE = "update client set passportNumber = :passportNumber, firstName = :firstName," +
            " lastName = :lastName, addDate = :addDate where clientId = :clientId";
    private static final String CLIENT_ID = "clientId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String INSERT = "insert into client (passportNumber, firstName, lastName, addDate) " +
            "values (:passportNumber, :firstName, :lastName, :addDate)";
    private static final String DELETE = "delete from client where clientId = :clientId";
    private static final String ADD_DATE = "addDate";
    private static final String PASSPORT_NUMBER = "passportNumber";
    private static final String SELECT_ALL_STUBS = "select clientId, firstName, lastName, addDate from client";
    private static final String FIND_STUB_BY_ID = "select clientId, firstName, lastName, addDate " +
            "from client where clientId = :clientId";
    private static final String SELECT_ALL_STUBS_BY_DATE = "SELECT clientId, firstName, lastName, addDate " +
            "FROM client WHERE addDate >= :startInterval AND addDate <= :endInterval GROUP BY clientId ";

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ClientDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<Client> findAll() {
        LOGGER.debug("findAll()");
        List<Client> clientDaoList = namedParameterJdbcTemplate.query(SELECT_ALL, new ClientDaoRowMapper());
        return clientDaoList.stream();
    }

    @Override
    public Stream<ClientStub> findAllStubs() {
        LOGGER.debug("findAllStubs()");
        List<ClientStub> clientStubs = namedParameterJdbcTemplate.query(SELECT_ALL_STUBS, new ClientStubRowMapper());
        return clientStubs.stream();
    }

    @Override
    public Optional<Client> findById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CLIENT_ID, clientId);
        Client client = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, namedParameters,
                BeanPropertyRowMapper.newInstance(Client.class));
        return Optional.ofNullable(client);
    }

    @Override
    public Optional<ClientStub> findStubById(Integer clientId) {
        LOGGER.debug("findStubById({})", clientId);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(CLIENT_ID, clientId);
        ClientStub clientStub = namedParameterJdbcTemplate.queryForObject(FIND_STUB_BY_ID, parameterSource,
                BeanPropertyRowMapper.newInstance(ClientStub.class));
        return Optional.ofNullable(clientStub);
    }

    @Override
    public Optional<Client> add(Client client) {
        LOGGER.debug("add({})", client);
        return Optional.of(client)
                .filter(this::isPassportNumberUnique)
                .map(this::insertClient)
                .orElseThrow(() -> new IllegalArgumentException("Client with the same number already exist in DB."));
    }

    @Override
    public Stream<ClientStub> findStubByDate(AddDateInterval interval) {
        LOGGER.debug("findStubByDate({}, {})", interval);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("startInterval", interval.getAddStartInterval());
        parameterSource.addValue("endInterval", interval.getAddEndInterval());
        List<ClientStub> clientStubs =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_STUBS_BY_DATE, parameterSource, new ClientDaoImpl.ClientStubRowMapper());
        return clientStubs.stream();
    }

    private boolean isPassportNumberUnique(Client client) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_PASSPORT_NUMBER,
                new MapSqlParameterSource(PASSPORT_NUMBER, client.getPassportNumber()), Integer.class) == 0;
    }

    private Optional<Client> insertClient(Client client) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(PASSPORT_NUMBER, client.getPassportNumber());
        mapSqlParameterSource.addValue(FIRST_NAME, client.getFirstName());
        mapSqlParameterSource.addValue(LAST_NAME, client.getLastName());
        mapSqlParameterSource.addValue(ADD_DATE, client.getAddDate());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        client.setClientId(generatedKeyHolder.getKey().intValue());
        return Optional.of(client);
    }

    private class ClientDaoRowMapper implements RowMapper<Client> {

        @Override
        public Client mapRow(ResultSet resultSet, int i) throws SQLException {
            Client client = new Client();
            client.setClientId(resultSet.getInt(CLIENT_ID));
            client.setPassportNumber(resultSet.getString(PASSPORT_NUMBER));
            client.setFirstName(resultSet.getString(FIRST_NAME));
            client.setLastName(resultSet.getString(LAST_NAME));
            client.setAddDate(resultSet.getDate(ADD_DATE));
            return client;
        }
    }


    private class ClientStubRowMapper implements RowMapper<ClientStub>{

        @Override
        public ClientStub mapRow(ResultSet resultSet, int i) throws SQLException {
            ClientStub clientStub = new ClientStub();
            clientStub.setClientId(resultSet.getInt(CLIENT_ID));
            clientStub.setAddDate(resultSet.getDate(ADD_DATE));
            clientStub.setFirstName(resultSet.getString(FIRST_NAME));
            clientStub.setLastName(resultSet.getString(LAST_NAME));
            return clientStub;
        }
    }

    @Override
    public void update(Client client) {
        Optional.of(namedParameterJdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(client)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update client in DB"));

    }

    @Override
    public void delete(int clientId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CLIENT_ID, clientId);
        Optional.of(namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete client from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }
}
