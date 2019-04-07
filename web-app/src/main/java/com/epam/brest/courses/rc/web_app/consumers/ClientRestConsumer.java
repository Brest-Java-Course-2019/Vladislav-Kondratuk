package com.epam.brest.courses.rc.web_app.consumers;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.service.ClientService;
import com.epam.brest.courses.rc.web_app.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ClientRestConsumer implements ClientService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    private String url;

    private RestTemplate restTemplate;

    public ClientRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Client> findAll() {
        LOGGER.debug("findAll");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<Client>) responseEntity.getBody();
    }

    @Override
    public List<ClientDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all-dto", List.class);
        return  (List<ClientDTO>) responseEntity.getBody();
    }

    @Override
    public Client findById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        ResponseEntity<Client> responseEntity = restTemplate.getForEntity(url + "/client/" + clientId,
                Client.class);
        return responseEntity.getBody();
    }

    @Override
    public ClientDTO findDTOById(Integer clientId) {
        LOGGER.debug("findById({})", clientId);
        ResponseEntity<ClientDTO> responseEntity = restTemplate.getForEntity(url + "/client/" + clientId,
                ClientDTO.class);
        return responseEntity.getBody();
    }

    @Override
    public List<ClientDTO> findDTOsByDate(String startDate, String endDate) {
        return null;
    }

    @Override
    public void add(Client client) {
        LOGGER.debug("add({})", client);
        restTemplate.postForEntity(url, client, Client.class);
    }

    @Override
    public void update(Client client) {
        LOGGER.debug("update({})", client);
        restTemplate.put(url, client);

    }

    @Override
    public void delete(int clientId) {
        LOGGER.debug("update({})", clientId);
        restTemplate.delete(url + "/client/" + clientId);
    }
}
