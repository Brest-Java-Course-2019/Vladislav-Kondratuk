package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.ClientDao;
import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     * @return list of all clients.
     */
    @Override
    public List<Client> findAll() {
        LOGGER.debug("findAll()");
        return dao.findAll().collect(Collectors.toList());
    }

    /**
     * Method findAllDTOs gets all clients DTO.
     *
     * @return list of all clients DTO.
     */
    @Override
    public List<ClientDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return dao.findAllDTOs().collect(Collectors.toList());
    }

    /**
     * Method findById get client by ID.
     *
     * @return client by ID.
     */
    @Override
    public Client findById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        return dao.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Failed to get client from DB"));
    }

    /**
     * Method findById get client DTO by ID.
     *
     * @return client DTO by ID.
     */
    @Override
    public ClientDTO findDTOById(Integer clientId) {
        LOGGER.debug("findDTOById({})", clientId);
        return dao.findDTOById(clientId)
                .orElseThrow(() -> new RuntimeException("Failed to get client dto from DB"));
    }

    /**
     * Method findDTOsByDate get clients by Date interval.
     *
     * Gets DTO client filtered by date.
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO client list filtered by date
     */
    @Override
    public List<ClientDTO> findDTOsByDate(final String startDate, final String endDate) {
        LOGGER.debug("findDTOsByDate({}, {})", startDate, endDate);
        return dao.findDTOsByDate(
                new ClientDateInterval(startDate, endDate)).collect(Collectors.toList());
    }

    /**
     * Method add new client.
     *
     * @param client new client.
     */
    @Override
    public void add(Client client) {
        LOGGER.debug("add({})", client);
        dao.add(client);
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
