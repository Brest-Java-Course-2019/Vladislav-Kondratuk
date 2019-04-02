package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})
class ClientRestControllerTest {

    private static final int ONCE = 1;

    private static final String START_DATE = "2019-01-20";

    private static final String END_DATE = "2019-01-22";

    @Autowired
    private ClientRestController controller;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @AfterEach
    void verifyAndReset() {
        Mockito.verifyNoMoreInteractions(clientService);
        Mockito.reset(clientService);
    }

    @Test
    void shouldFindAllClients() throws Exception {
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(createClient(0, "0")
                , createClient(1, "1")));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/all")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].passportNumber", Matchers.is("AB41240")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Alex0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Ivanov0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].passportNumber", Matchers.is("AB41241")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Alex1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Ivanov1")))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllClientDTOs() throws Exception {
        Mockito.when(clientService.findAllDTOs()).thenReturn(Arrays.asList(createClientDTO(0, "0")
                , createClientDTO(1, "1")));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/all-dto")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Alex0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Ivanov0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfOrders", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Alex1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Ivanov1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfOrders", Matchers.is(1)))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindClientById() throws Exception {
        Mockito.when(clientService.findById(Mockito.anyInt()))
                .thenReturn(createClient(1, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/client/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", Matchers.is("AB41241")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Alex1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Ivanov1")))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindClientDTOById() throws Exception {
        Mockito.when(clientService.findDTOById(Mockito.anyInt()))
                .thenReturn(createClientDTO(1, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/dto/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Alex1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Ivanov1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfOrders", Matchers.is(1)))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindClientDTOsByDate() throws Exception {
        Mockito.when(clientService.findDTOsByDate(START_DATE, END_DATE))
                .thenReturn(Arrays.asList(createClientDTO(0, "0")
                        , createClientDTO(1, "1")));


        mockMvc.perform(MockMvcRequestBuilders.get("/clients/dto/{startDate}/{endDate}",
                START_DATE, END_DATE)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Alex0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Ivanov0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfOrders", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Alex1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", Matchers.is("Ivanov1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfOrders", Matchers.is(1)))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE))
                .findDTOsByDate(START_DATE, END_DATE);
    }

    @Test
    void shouldAddNewClient() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).add(Mockito.any(Client.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createClient(1, "1")))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).add(Mockito.any(Client.class));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).update(Mockito.any(Client.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/clients")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createClient(1, "1")))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).update(Mockito.any(Client.class));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).delete(Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/client/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createClient(1, "1")))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).delete(Mockito.anyInt());
    }

    private Client createClient(int index, String stringIndex) {
        Client client = new Client();
        client.setClientId(index);
        client.setPassportNumber("AB4124" + stringIndex);
        client.setFirstName("Alex" + stringIndex);
        client.setLastName("Ivanov" + stringIndex);
        return client;
    }

    private ClientDTO createClientDTO(int index, String stringIndex) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(index);
        clientDTO.setFirstName("Alex" + stringIndex);
        clientDTO.setLastName("Ivanov" + stringIndex);
        clientDTO.setNumberOfOrders(index);
        return clientDTO;
    }
}