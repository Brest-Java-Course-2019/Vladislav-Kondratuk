package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * DAO Interface of client.
 */
public interface ClientDao {

    /**
     * Get all clients.
     *
     * @return clients stream.
     */
    Stream<Client> findAll();

    /**
     * Get all DTO clients.
     *
     * @return DTO clients stream.
     */
    Stream<ClientDTO> findAllDTOs();

    /**
     * Get client by ID.
     *
     * @param clientId client ID for getting.
     * @return client by ID.
     */
    Optional<Client> findById(Integer clientId);

    /**
     * Get DTO client by ID.
     *
     * @param clientId DTO client ID for getting.
     * @return DTO client by ID.
     */
    Optional<ClientDTO> findDTOById(Integer clientId);

    /**
     * Gets DTO clients between certain dates.
     *
     * @param interval date range for compare.
     * @return DTO clients stream filtered by date.
     */
    Stream<ClientDTO> findDTOsByDate(ClientDateInterval interval);

    /**
     * Add new client.
     *
     * @param client new client.
     * @return new client.
     */
    Optional<Client> add(Client client);

    /**
     * Update client.
     *
     * @param client client for updating.
     */
    void update(Client client);

    /**
     * Delete client with specified ID.
     *
     * @param clientId client ID for delete.
     */
    void delete(int clientId);

}
