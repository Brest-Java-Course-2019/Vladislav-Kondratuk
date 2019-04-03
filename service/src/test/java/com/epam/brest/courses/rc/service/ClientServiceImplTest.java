package com.epam.brest.courses.rc.service;

import com.epam.brest.courses.rc.dao.ClientDao;
import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderServiceImplTest.class);

    private static final int CLIENT_ID = 1;
    private static final String PASSPORT_NUMBER = "AB75612";
    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Hrabrova";
    private static final Date ADD_DATE = Date.valueOf("2019-01-24");
    private static final int EXPECTED_NUMBER_OF_CLIENTS = 1;
    private static final int ONCE = 1;
    private static final int CLIENT_ID_DTO = 2;
    private static final String FIRST_NAME_DTO = "Alex";
    private static final String LAST_NAME_DTO = "Petrov";
    private static final Date ADD_DATE_DTO = Date.valueOf("2019-01-21");
    private static final int NUMBER_OF_ORDERS_DTO = 1;
    private static final String START_DATE = "2019-01-20";
    private static final String END_DATE = "2019-01-22";

    private ClientService service;

    private ClientDao dao;

    private Client client = new Client();

    private ClientDTO clientDTO = new ClientDTO();

    private Client createClient() {
        client.setClientId(CLIENT_ID);
        client.setPassportNumber(PASSPORT_NUMBER);
        client.setFirstName(FIRST_NAME);
        client.setLastName(LAST_NAME);
        client.setAddDate(ADD_DATE);
        return client;
    }

    private ClientDTO createClientDTO() {
        clientDTO.setClientId(CLIENT_ID_DTO);
        clientDTO.setFirstName(FIRST_NAME_DTO);
        clientDTO.setLastName(LAST_NAME_DTO);
        clientDTO.setAddDate(ADD_DATE_DTO);
        clientDTO.setNumberOfOrders(NUMBER_OF_ORDERS_DTO);
        return clientDTO;
    }

    @BeforeEach
    void setup() {
        dao = Mockito.mock(ClientDao.class);
        service = new ClientServiceImpl(dao);
    }

    @AfterEach
    void verifyAndReset() {
        Mockito.verifyNoMoreInteractions(dao);
        Mockito.reset(dao);
    }

    @Test
    void shouldFindAllClients() {
        LOGGER.debug("run test shouldFindAllClients()");

        Mockito.when(dao.findAll()).thenReturn(Stream.of(createClient()));

        List<Client> clientStream = service.findAll();
        assertEquals(EXPECTED_NUMBER_OF_CLIENTS, clientStream.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllDTOs() {
        LOGGER.debug("run test shouldFindAllDTOs()");

        Mockito.when(dao.findAllDTOs()).thenReturn(Stream.of(createClientDTO()));

        List<ClientDTO> clientDTOStream = service.findAllDTOs();
        assertEquals(EXPECTED_NUMBER_OF_CLIENTS, clientDTOStream.size());

        Mockito.verify(dao, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindClientById() {
        LOGGER.debug("run test shouldFindClientById()");

        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(createClient()));

        Client client = service.findById(CLIENT_ID);
        assertEquals(client, createClient());

        Mockito.verify(dao, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOById() {
        LOGGER.debug("run test shouldFindDTOById()");

        Mockito.when(dao.findDTOById(Mockito.anyInt())).thenReturn(Optional.of(createClientDTO()));

        ClientDTO clientDTO = service.findDTOById(CLIENT_ID_DTO);
        assertEquals(clientDTO, createClientDTO());

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindDTOsByDate() {
        LOGGER.debug("run test shouldFindDTOsByDate()");

        Mockito.when(dao.findDTOsByDate(Mockito.any(ClientDateInterval.class)))
                .thenReturn(Stream.of(createClientDTO()));

        List<ClientDTO> clientDTOS = service.findDTOsByDate(START_DATE, END_DATE);
        assertNotNull(clientDTOS);

        Mockito.verify(dao, Mockito.times(ONCE)).findDTOsByDate(Mockito.any(ClientDateInterval.class));
    }

    @Test
    void shouldAddNewClient() {
        LOGGER.debug("run test shouldAddNewClient()");

        Mockito.when(dao.add(Mockito.any(Client.class))).thenReturn(Optional.of(createClient()));
        service.add(createClient());
        Mockito.verify(dao, Mockito.times(ONCE)).add(createClient());
    }

    @Test
    void shouldUpdateClient() {
        LOGGER.debug("run test shouldUpdateClient()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).update(Mockito.any(Client.class));
        service.update(createClient());
        Mockito.verify(dao, Mockito.times(ONCE)).update(createClient());
    }

    @Test
    void shouldDeleteClient() {
        LOGGER.debug("run test shouldDeleteClient()");

        Mockito.doNothing().doThrow(new IllegalStateException()).when(dao).delete(CLIENT_ID);
        Mockito.when(dao.findById(CLIENT_ID)).thenReturn(Optional.of(createClient()));
        service.delete(CLIENT_ID);
        Mockito.verify(dao,Mockito.times(ONCE)).delete(CLIENT_ID);
    }
}