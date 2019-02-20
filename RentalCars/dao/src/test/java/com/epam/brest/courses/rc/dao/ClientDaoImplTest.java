package com.epam.brest.courses.rc.dao;

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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback
class ClientDaoImplTest {

    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Hrabrova";
    private static final int FULL_CLIENTS_LIST = 4;
    private static final int CLIENT_ID = 2;
    private static final String NEW_FIRST_NAME = "Vlad";
    private static final String NEW_LAST_NAME = "Popov";
    @Autowired
    private ClientDao clientDao;

    @Test
    void findAll() {
        Stream<Client> clients = clientDao.findAll();
        assertNotNull(clients);
    }

    @Test
    void findAllCheckCount() {
        Stream<Client> clients = clientDao.findAll();
        assertNotNull(clients);
        assertEquals(FULL_CLIENTS_LIST, clients.count());
    }

    @Test
    void findById() {
        Client client = clientDao.findById(2).get();
        assertNotNull(client);
        assertEquals(CLIENT_ID, client.getClientId().intValue());
        assertEquals(FIRST_NAME, client.getFirstName());
        assertEquals(LAST_NAME, client.getLastName());
    }

    @Test
    void create() {
        Stream<Client> clientBeforeInsert = clientDao.findAll();

        Client client = new Client();
        client.setFirstName(NEW_FIRST_NAME);
        client.setLastName(NEW_LAST_NAME);
        Client newClient = clientDao.add(client).get();
        assertNotNull(newClient.getClientId());
        Stream<Client> clientAfterInsert = clientDao.findAll();

        assertEquals(1, clientAfterInsert.count() - clientBeforeInsert.count());
    }

    @Test
    void createDuplicateClient() {
        Client client2 = new Client();
        client2.setFirstName(NEW_FIRST_NAME);
        client2.setLastName(NEW_LAST_NAME);
        Client newClient = clientDao.add(client2).get();
        assertNotNull(newClient.getClientId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientDao.add(client2);
        });
    }

    @Test
    void update() {
        Client client = new Client();
        client.setFirstName(NEW_FIRST_NAME);
        client.setLastName(NEW_LAST_NAME);
        Client newClient = clientDao.add(client).get();
        assertNotNull(newClient.getClientId());

        client.setFirstName(NEW_FIRST_NAME + "_2");
        client.setLastName(NEW_LAST_NAME + "_2");
        clientDao.update(client);

        Client updatedClient = clientDao.findById(client.getClientId()).get();

        assertEquals(NEW_FIRST_NAME + "_2", updatedClient.getFirstName());
        assertEquals(NEW_LAST_NAME + "_2", updatedClient.getLastName());
    }

    @Test
    void delete() {
        Stream<Client> clients = clientDao.findAll();
        Client client = clients.findFirst().get();
        clientDao.delete(client.getClientId());

        Assertions.assertThrows(DataAccessException.class, () -> {
            clientDao.findById(client.getClientId());
        });
    }
}
