package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.ClientDao;
import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of ClientService. Gets data from dao and database.
 */
@Transactional
public class ClientServiceImpl implements ClientService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImpl.class);

    /**
     * DAO.
     */
    private ClientDao dao;

    /**
     * Service constructor.
     * @param dao dao of client objects.
     */
    public ClientServiceImpl(ClientDao dao) {
        this.dao = dao;
    }

    /**
     * Method findAll gets all clients.
     *
     * @return stream of all clients.
     */
    @Override
    public Stream<Client> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll();
    }

    /**
     * Method findAllDTOs gets all clients DTO.
     *
     * @return stream of all clients DTO.
     */
    @Override
    public Stream<ClientDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs();
    }

    /**
     * Method findById get client by ID.
     *
     * @return client by ID.
     */
    @Override
    public Optional<Client> findById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        return dao.findById(clientId);
    }

    /**
     * Method findById get client DTO by ID.
     *
     * @return client DTO by ID.
     */
    @Override
    public Optional<ClientDTO> findDTOById(Integer clientId) {
        LOGGER.debug("findDTOById({})", clientId);
        return dao.findDTOById(clientId);
    }

    /**
     * Method findDTOsByDate get clients by Date interval.
     *
     * @param interval date range for compare.
     * @return clients by Date interval.
     */
    @Override
    public Stream<ClientDTO> findDTOsByDate(ClientDateInterval interval) {
        LOGGER.debug("findDTOsByDate({})", interval);
        return dao.findDTOsByDate(interval);
    }

    /**
     * Method add new client.
     *
     * @param client new client.
     * @return new client.
     */
    @Override
    public Optional<Client> add(Client client) {
        LOGGER.debug("add({})", client);
        return dao.add(client);
    }

    /**
     * Method update client.
     *
     * @param client client for updating.
     */
    @Override
    public void update(Client client) {
        LOGGER.debug("update({})", client);
        dao.update(client);
    }

    /**
     * Method delete client by ID.
     *
     * @param clientId client ID for delete.
     */
    @Override
    public void delete(int clientId) {
        LOGGER.debug("delete({})", clientId);
        dao.delete(clientId);
    }
}
