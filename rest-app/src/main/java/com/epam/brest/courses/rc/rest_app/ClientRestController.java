package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for clients.
 */
@RestController
@RequestMapping(value = "/clients")
public class ClientRestController implements ClientService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    /**
     * Service.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Gets clients.
     * @return list of clients.
     *
     * curl -X GET -v http://localhost:8088/clients/all
     */
    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Client> findAll() {
        LOGGER.debug("findAll()");
        return clientService.findAll();
    }

    /**
     * Gets DTO clients.
     * @return list of DTO clients.
     *
     * curl -X GET -v http://localhost:8088/clients/all-dto
     */
    @Override
    @RequestMapping(value = "/all-dto", method = RequestMethod.GET)
    public List<ClientDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return clientService.findAllDTOs();
    }

    /**
     * Gets client by ID.
     * @return client by ID.
     *
     * curl -X GET -v http://localhost:8088/clients/client/1
     */
    @Override
    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET)
    public Client findById(@PathVariable Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        return clientService.findById(clientId);
    }

    /**
     * Gets DTO client by ID.
     * @return DTO client by ID.
     *
     * curl -X GET -v http://localhost:8088/clients/dto/1
     */
    @Override
    @RequestMapping(value = "/dto/{clientId}", method = RequestMethod.GET)
    public ClientDTO findDTOById(@PathVariable Integer clientId) {
        LOGGER.debug("findDTOById({})", clientId);
        return clientService.findDTOById(clientId);
    }

    /**
     * Gets DTO client filtered by date.
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO client list filtered by date.
     *
     * curl -X GET -v http://localhost:8088/clients/dto/2019-01-20/2019-01-22
     */
    @Override
    @RequestMapping(value = "/dto/{startDate}/{endDate}", method = RequestMethod.GET)
    public List<ClientDTO> findDTOsByDate(@PathVariable(value = "startDate") final String startDate,
                                          @PathVariable(value = "endDate") final String endDate) {
        LOGGER.debug("findDTOsByDate({}, {})", startDate, endDate);
        return clientService.findDTOsByDate(startDate, endDate);
    }

    /**
     * Adds new client.
     * @param client new client.
     *
     *  curl -H "Content-Type: application/json" -X POST -d '{"clientId":"5","passportNumber":"AB41245",
     *          "firstName":"Alex","lastName":"Puchkov","addDate":"2019-02-08"}' -v http://localhost:8088/clients
     */
    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Client client) {
        LOGGER.debug("add client({})", client);
        clientService.add(client);
    }

    /**
     * Update client.
     * @param client client for updating.
     *
     *  curl -H "Content-Type: application/json" -X PUT -d '{"clientId":"5","passportNumber":"AC457412",
     *          "firstName":"Alex","lastName":"Ivanov","addDate":"2019-02-08"}' -v http://localhost:8088/clients
     */
    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Client client) {
        LOGGER.debug("update client({})", client);
        clientService.update(client);
    }

    /**
     * Delete client by ID.
     * @param clientId client ID for delete.
     *
     * curl -X DELETE -v http://localhost:8088/clients/client/5
     */
    @Override
    @RequestMapping(value = "/client/{clientId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int clientId) {
        LOGGER.debug("delete client({})", clientId);
        clientService.delete(clientId);
    }
}
