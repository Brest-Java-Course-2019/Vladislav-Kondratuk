package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
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
 * Implementation of ClientDao. Gets data from database.
 */
public class ClientDaoImpl implements ClientDao {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    /**
     * Constant fields.
     */
    private static final String CLIENT_ID = "clientId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PASSPORT_NUMBER = "passportNumber";
    private static final String ADD_DATE = "addDate";
    private static final String NUMBER_OF_ORDERS = "numberOfOrders";
    private static final String END_INTERVAL = "endInterval";
    private static final String START_INTERVAL = "startInterval";

    /**
     * SQL Select-all clients String.
     */
    @Value("${client.selectAll}")
    private String getAllClientsSql;

    /**
     * SQL Select-By-Id client String.
     */
    @Value("${client.selectById}")
    private String getClientByIdSql;

    /**
     * SQL Insert new client String.
     */
    @Value("${client.insert}")
    private String addClientSql;

    /**
     * SQL Update client String.
     */
    @Value("${client.update}")
    private String updateClientSql;

    /**
     * SQL Delete-By-Id client String.
     */
    @Value("${client.delete}")
    private String deleteClientSql;

    /**
     * SQL Select-all DTO clients String.
     */
    @Value("${clientDTO.selectAll}")
    private String getAllDTOClientsSql;

    /**
     * SQL Check count unique client passport number String.
     */
    @Value("${client.checkCountPassportNumber}")
    private String checkCountPassportNumberSql;

    /**
     * SQL Select-By-Id DTO client String.
     */
    @Value("${clientDTO.selectById}")
    private String getClientDTOByIdSql;

    /**
     * SQL Select-By-Date DTO clients String.
     */
    @Value("${clientDTO.selectByDate}")
    private String getClientDTOByDateSql;

    /**
     * Property namedParameterJdbcTemplate.
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * method setNamedParameterJdbcTemplate setter method for namedParameterJdbcTemplate property.
     *
     * @param namedParameterJdbcTemplate input value.
     */
    public ClientDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Get all clients.
     *
     * @return clients stream.
     */
    @Override
    public Stream<Client> findAll() {
        LOGGER.debug("findAll()");
        List<Client> clientDaoList = namedParameterJdbcTemplate.query(getAllClientsSql, new ClientDaoRowMapper());
        return clientDaoList.stream();
    }

    /**
     * Get all DTO clients.
     *
     * @return DTO clients stream.
     */
    @Override
    public Stream<ClientDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        List<ClientDTO> clientDTOList = namedParameterJdbcTemplate.query(getAllDTOClientsSql, new ClientDTORowMapper());
        return clientDTOList.stream();
    }

    /**
     * Get client by ID.
     *
     * @param clientId client ID for getting.
     * @return client by ID.
     */
    @Override
    public Optional<Client> findById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CLIENT_ID, clientId);
        Client client = namedParameterJdbcTemplate.queryForObject(getClientByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(Client.class));
        return Optional.ofNullable(client);
    }

    /**
     * Get DTO client by ID.
     *
     * @param clientId DTO client ID for getting.
     * @return DTO client by ID.
     */
    @Override
    public Optional<ClientDTO> findDTOById(Integer clientId) {
        LOGGER.debug("findDTOById({})", clientId);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CLIENT_ID, clientId);
        ClientDTO clientDTO = namedParameterJdbcTemplate.queryForObject(getClientDTOByIdSql, namedParameters,
                BeanPropertyRowMapper.newInstance(ClientDTO.class));
        return Optional.ofNullable(clientDTO);
    }

    /**
     * Gets DTO clients between certain dates.
     *
     * @param interval date range for compare.
     * @return DTO clients stream filtered by date.
     */
    @Override
    public Stream<ClientDTO> findDTOsByDate(ClientDateInterval interval) {
        LOGGER.debug("findDTOsByDate({})", interval);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(START_INTERVAL, interval.getAddStartInterval());
        parameterSource.addValue(END_INTERVAL, interval.getAddEndInterval());
        List<ClientDTO> clientDTOList =
                namedParameterJdbcTemplate
                        .query(getClientDTOByDateSql, parameterSource, new ClientDTORowMapper());
        return clientDTOList.stream();
    }

    /**
     * Add new client.
     *
     * @param client new client.
     * @return new client.
     */
    @Override
    public Optional<Client> add(Client client) {
        LOGGER.debug("add({})", client);
        return Optional.of(client)
                .filter(this::isPassportNumberUnique)
                .map(this::insertClient)
                .orElseThrow(() -> new IllegalArgumentException("Client with the same number already exist in DB."));
    }

    /**
     * Check is new client passport number unique, before insert.
     *
     * @param client new client.
     * @return true if client passport number is unique.
     */
    private boolean isPassportNumberUnique(Client client) {
        return namedParameterJdbcTemplate.queryForObject(checkCountPassportNumberSql,
                new MapSqlParameterSource(PASSPORT_NUMBER, client.getPassportNumber()), Integer.class) == 0;
    }

    /**
     * Insert values for new client.
     *
     * @param client to set values for new client.
     * @return new client.
     */
    private Optional<Client> insertClient(Client client) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(PASSPORT_NUMBER, client.getPassportNumber());
        mapSqlParameterSource.addValue(FIRST_NAME, client.getFirstName());
        mapSqlParameterSource.addValue(LAST_NAME, client.getLastName());
        mapSqlParameterSource.addValue(ADD_DATE, client.getAddDate());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(addClientSql, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add(result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        client.setClientId(generatedKeyHolder.getKey().intValue());
        return Optional.of(client);
    }

    /**
     * ClientDaoRowMapper - for creating models from resultSet.
     */
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

    /**
     * ClientDTORowMapper - for creating models from resultSet.
     */
    private class ClientDTORowMapper implements RowMapper<ClientDTO> {

        @Override
        public ClientDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setClientId(resultSet.getInt(CLIENT_ID));
            clientDTO.setFirstName(resultSet.getString(FIRST_NAME));
            clientDTO.setLastName(resultSet.getString(LAST_NAME));
            clientDTO.setAddDate(resultSet.getDate(ADD_DATE));
            clientDTO.setNumberOfOrders(resultSet.getInt(NUMBER_OF_ORDERS));
            return clientDTO;
        }
    }

    /**
     * Update client.
     *
     * @param client client for updating.
     */
    @Override
    public void update(Client client) {
        Optional.of(namedParameterJdbcTemplate.update(updateClientSql, new BeanPropertySqlParameterSource(client)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update client in DB"));
    }

    /**
     * Delete client with specified ID.
     *
     * @param clientId client ID for delete.
     */
    @Override
    public void delete(int clientId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(CLIENT_ID, clientId);
        Optional.of(namedParameterJdbcTemplate.update(deleteClientSql, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete client from DB"));
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
