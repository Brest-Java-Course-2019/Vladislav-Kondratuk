package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.dto.ClientDTO;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.service.ClientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/root-context.xml", "classpath:web-spring-test.xml"})
class ClientControllerTest {

    private static final String START_DATE = "2019-01-08";
    private static final String END_DATE = "2019-01-11";

    private static final int ONCE = 1;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldGetClientsPage() throws Exception {
        Mockito.when(clientService.findAllDTOs()).thenReturn(Arrays.asList(createClientDTO(ZERO)
                , createClientDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/clients")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("clients"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Clients list</title>")))
        ;
        Mockito.verify(clientService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldGetAddClientPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/add-client")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers
                        .containsString("<title>Add client</title>")))
        ;
    }

    @Test
    void shouldGetEditClientPage() throws Exception {
        Mockito.when(clientService.findById(Mockito.anyInt())).thenReturn(createClient(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/edit-client/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<title>Edit client</title>")))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldAddClient() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).add(createClient(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("passportNumber", "AB")
                        .param("firstName", "first")
                        .param("lastName", "last")
                        .param("addDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/clients"))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).add(Mockito.any(Client.class));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).update(createClient(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("passportNumber", "AB")
                        .param("firstName", "first")
                        .param("lastName", "last")
                        .param("addDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/clients"))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).update(Mockito.any(Client.class));
    }

    @Test
    void shouldGetAddClientValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).add(createClient(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("firstName", "first")
                        .param("lastName", "last")
                        .param("addDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("passportNumber"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldGetUpdateClientValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).update(createClient(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("firstName", "first")
                        .param("lastName", "last")
                        .param("addDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("passportNumber"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldDeleteClientById() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(clientService).delete(Mockito.anyInt());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/client/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/clients"))
        ;

        Mockito.verify(clientService, Mockito.times(ONCE)).delete(Mockito.anyInt());
    }

    @Test
    void shouldGetFilterOrdersValidationErrorPage() throws Exception {
        Mockito.when(clientService.findAllDTOs())
                .thenReturn(Arrays.asList(createClientDTO(ZERO), createClientDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/filter-clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("addStartInterval", "")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("addEndInterval"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("addStartInterval"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("clients"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Clients list</title>")))
        ;
    }

    @Test
    void shouldGetFilterOrdersPage() throws Exception {
        Mockito.when(clientService.findDTOsByDate(START_DATE, END_DATE))
                .thenReturn(Collections.singletonList(createClientDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/filter-clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("addStartInterval", START_DATE)
                        .param("addEndInterval", END_DATE)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("clients"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Clients list</title>")))
        ;
    }

    private Client createClient(int index) {
        Client client = new Client();
        client.setClientId(index);
        client.setPassportNumber("AB4124" + index);
        client.setFirstName("Alex" + index);
        client.setLastName("Ivanov" + index);
        return client;
    }

    private ClientDTO createClientDTO(int index) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(index);
        clientDTO.setFirstName("Alex" + index);
        clientDTO.setLastName("Ivanov" + index);
        clientDTO.setNumberOfOrders(index);
        return clientDTO;
    }
}