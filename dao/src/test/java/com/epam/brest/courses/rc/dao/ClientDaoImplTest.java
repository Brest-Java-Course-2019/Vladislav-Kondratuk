package com.epam.brest.courses.rc.dao;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:dao.xml", "classpath:test-dao.xml", "classpath:test-db.xml"})
@Transactional
@Rollback
class ClientDaoImplTest {

    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Hrabrova";
    private static final Date ADD_DATE = Date.valueOf("2019-01-24");
    private static final String PASSPORT_NUMBER = "AB75612";
    private static final int CLIENT_ID = 2;
    private static final int FULL_CLIENTS_LIST = 4;
    private static final String NEW_FIRST_NAME = "Vlad";
    private static final String NEW_LAST_NAME = "Popov";
    private static final String NEW_PASSPORT_NUMBER = "AB54122";
    private static final String NEW_ADD_DATE = "2019-02-09";
    private static final String EXPECTED_DATE = "2002-04-12";
    private static final int DTO_CLIENT_ID = 1;
    private static final String DTO_FIRST_NAME = "Alex";
    private static final String DTO_LAST_NAME = "Petrov";
    private static final Date DTO_ADD_DATE = Date.valueOf("2019-01-21");
    private static final int DTO_NUMBER_OF_ORDERS = 1;
    private static final ClientDateInterval ADD_DATE_INTERVAL1 = new ClientDateInterval
            ("2019-01-23","2019-02-02");
    private static final ClientDateInterval ADD_DATE_INTERVAL2 = new ClientDateInterval
            ("2019-02-06","2019-02-07");

    @Autowired
    private ClientDao clientDao;

    @Test
    void shouldFindAllClients() {
        Stream<Client> clients = clientDao.findAll();
        assertNotNull(clients);
        assertTrue(clients.count() > 0);
    }

    @Test
    void shouldFindAllDTOs() {
        Stream<ClientDTO> clientsDTO = clientDao.findAllDTOs();
        assertNotNull(clientsDTO);
        assertTrue(clientsDTO.count() > 0);
    }

    @Test
    void shouldFindClientById() {
        Client client = clientDao.findById(2).get();
        assertNotNull(client);
        assertEquals(CLIENT_ID, client.getClientId().intValue());
        assertEquals(PASSPORT_NUMBER, client.getPassportNumber());
        assertEquals(FIRST_NAME, client.getFirstName());
        assertEquals(LAST_NAME, client.getLastName());
        assertEquals(ADD_DATE, client.getAddDate());
    }

    @Test
    void shouldFindAllClientsListCheckCount() {
        Stream<Client> clients = clientDao.findAll();
        assertNotNull(clients);
        assertEquals(FULL_CLIENTS_LIST, clients.count());
    }

    @Test
    void shouldFindDTOById() {
        ClientDTO clientDTO = clientDao.findDTOById(1).get();
        assertNotNull(clientDTO);
        assertEquals(DTO_CLIENT_ID, clientDTO.getClientId().intValue());
        assertEquals(DTO_FIRST_NAME, clientDTO.getFirstName());
        assertEquals(DTO_LAST_NAME, clientDTO.getLastName());
        assertEquals(DTO_ADD_DATE, clientDTO.getAddDate());
        assertEquals(DTO_NUMBER_OF_ORDERS, clientDTO.getNumberOfOrders().intValue());
    }

    @Test
    void shouldFindDTOsByDate() {
        Stream<ClientDTO> clientDTO1 = clientDao.findDTOsByDate(ADD_DATE_INTERVAL1);
        Stream<ClientDTO> clientDTO2 = clientDao.findDTOsByDate(ADD_DATE_INTERVAL2);
        assertNotNull(clientDTO1);
        assertNotNull(clientDTO2);
        assertEquals(2, clientDTO1.count());
        assertEquals(1, clientDTO2.count());
    }

    @Test
    void shouldAddNewClient() {
        Stream<Client> clientBeforeInsert = clientDao.findAll();

        Client client = new Client();
        client.setPassportNumber(NEW_PASSPORT_NUMBER);
        client.setFirstName(NEW_FIRST_NAME);
        client.setLastName(NEW_LAST_NAME);
        client.setAddDate(Date.valueOf(NEW_ADD_DATE));
        Client newClient = clientDao.add(client).get();
        assertNotNull(newClient.getClientId());
        Stream<Client> clientAfterInsert = clientDao.findAll();

        assertEquals(1, clientAfterInsert.count() - clientBeforeInsert.count());
    }

    @Test
    void shouldCreateDuplicateClientException() {
        Client client2 = new Client();
        client2.setPassportNumber(NEW_PASSPORT_NUMBER);
        client2.setFirstName(NEW_FIRST_NAME);
        client2.setLastName(NEW_LAST_NAME);
        client2.setAddDate(Date.valueOf(NEW_ADD_DATE));
        Client newClient = clientDao.add(client2).get();
        assertNotNull(newClient.getClientId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> clientDao.add(client2));
    }

    @Test
    void shouldUpdateClient() {
        Client client = new Client();
        client.setPassportNumber(NEW_PASSPORT_NUMBER);
        client.setFirstName(NEW_FIRST_NAME);
        client.setLastName(NEW_LAST_NAME);
        client.setAddDate(Date.valueOf(NEW_ADD_DATE));
        Client newClient = clientDao.add(client).get();
        assertNotNull(newClient.getClientId());

        client.setPassportNumber(NEW_PASSPORT_NUMBER + "_2");
        client.setFirstName(NEW_FIRST_NAME + "_2");
        client.setLastName(NEW_LAST_NAME + "_2");
        client.setAddDate(Date.valueOf(EXPECTED_DATE));
        clientDao.update(client);

        Client updatedClient = clientDao.findById(client.getClientId()).get();

        assertEquals(NEW_PASSPORT_NUMBER + "_2", updatedClient.getPassportNumber());
        assertEquals(NEW_FIRST_NAME + "_2", updatedClient.getFirstName());
        assertEquals(NEW_LAST_NAME + "_2", updatedClient.getLastName());
        assertEquals(Date.valueOf(EXPECTED_DATE), updatedClient.getAddDate());
    }

    @Test
    void shouldDeleteClientById() {
        Client client = new Client();
        client.setPassportNumber(NEW_PASSPORT_NUMBER);
        client.setFirstName(NEW_FIRST_NAME);
        client.setLastName(NEW_LAST_NAME);
        client.setAddDate(Date.valueOf(NEW_ADD_DATE));
        Client newClient = clientDao.add(client).get();

        assertNotNull(newClient.getClientId());

        clientDao.delete(newClient.getClientId());

        Assertions.assertThrows(DataAccessException.class, () -> clientDao.findById(client.getClientId()));
    }
}