package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service interface of client.
 */
public interface ClientService {

    /**
     * Get all clients from DAO.
     *
     * @return clients stream.
     */
    Stream<Client> findAll();

    /**
     * Get all DTO clients from DAO.
     *
     * @return DTO clients stream.
     */
    Stream<ClientDTO> findAllDTOs();

    /**
     * Get client by ID from DAO.
     *
     * @param clientId client ID for getting.
     * @return client by ID.
     */
    Optional<Client> findById(Integer clientId);

    /**
     * Get DTO client by ID from DAO.
     *
     * @param clientId DTO client ID for getting.
     * @return DTO client by ID.
     */
    Optional<ClientDTO> findDTOById(Integer clientId);

    /**
     * Gets DTO clients between certain dates from DAO.
     *
     * @param interval date range for compare.
     * @return DTO clients stream filtered by date.
     */
    Stream<ClientDTO> findDTOsByDate(ClientDateInterval interval);

    /**
     * Add new client to DAO.
     *
     * @param client new client.
     * @return new client.
     */
    Optional<Client> add(Client client);

    /**
     * Update client in DAO.
     *
     * @param client client for updating.
     */
    void update(Client client);

    /**
     * Delete client with specified ID from DAO.
     *
     * @param clientId client ID for delete.
     */
    void delete(int clientId);
}
