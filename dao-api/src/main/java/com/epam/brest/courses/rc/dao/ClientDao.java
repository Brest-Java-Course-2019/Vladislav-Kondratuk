package com.epam.brest.courses.rc.dao;

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
     * Get client by ID.
     *
     * @param clientId client ID for getting.
     * @return client by ID.
     */
    Optional<Client> findById(Integer clientId);

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
