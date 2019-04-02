package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.model.Client;

import java.util.List;

/**
 * Service interface of client.
 */
public interface ClientService {

    /**
     * Get all clients from DAO.
     *
     * @return clients list.
     */
    List<Client> findAll();

    /**
     * Get all DTO clients from DAO.
     *
     * @return DTO clients list.
     */
    List<ClientDTO> findAllDTOs();

    /**
     * Get client by ID from DAO.
     *
     * @param clientId client ID for getting.
     * @return client by ID.
     */
    Client findById(Integer clientId);

    /**
     * Get DTO client by ID from DAO.
     *
     * @param clientId DTO client ID for getting.
     * @return DTO client by ID.
     */
    ClientDTO findDTOById(Integer clientId);

    /**
     * Gets DTO clients between certain dates from DAO.
     *
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO rental orders filtered by date.
     */
    List<ClientDTO> findDTOsByDate(String startDate, String endDate);

    /**
     * Add new client to DAO.
     *
     * @param client new client.
     */
    void add(Client client);

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
