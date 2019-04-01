package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
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

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})
class RentalOrderRestControllerTest {

    private static final int ONCE = 1;

    private static final String START_DATE = "2019-01-08";

    private static final String END_DATE = "2019-01-11";

    @Autowired
    private RentalOrderRestController controller;

    @Autowired
    private RentalOrderService rentalOrderService;

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
        Mockito.verifyNoMoreInteractions(rentalOrderService);
        Mockito.reset(rentalOrderService);
    }

    @Test
    void shouldFindAllRentalOrders() throws Exception {
        Mockito.when(rentalOrderService.findAll()).thenReturn(Arrays.asList(createOrder(0)
                , createOrder(1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/all")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalTime", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalTime", Matchers.is(2)))
                ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllRentalOrdersDTO() throws Exception {
        Mockito.when(rentalOrderService.findAllDTOs()).thenReturn(Arrays.asList(createOrderDTO(0, "0")
                , createOrderDTO(1, "1")));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/all-dto")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].passportNumber", Matchers.is("AB42120")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carNumber", Matchers.is("AC4120")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalTime", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalCost", Matchers.is(70)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalCost", Matchers.is(140)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].passportNumber", Matchers.is("AB42121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carNumber", Matchers.is("AC4121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalTime", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalCost", Matchers.is(71)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].totalCost", Matchers.is(141)))
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindRentalOrderById() throws Exception {
        Mockito.when(rentalOrderService.findById(Mockito.anyInt())).thenReturn(createOrder(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/order/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalTime", Matchers.is(2)))
                ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindRentalOrderDTOById() throws Exception {
        Mockito.when(rentalOrderService.findDTOById(Mockito.anyInt()))
                .thenReturn(createOrderDTO(1, "1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/dto/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", Matchers.is("AB42121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carNumber", Matchers.is("AC4121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalTime", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalCost", Matchers.is(71)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCost", Matchers.is(141)))
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindRentalOrderDTOsByDate() throws Exception {
        Mockito.when(rentalOrderService.findDTOsByDate(START_DATE, END_DATE))
                .thenReturn(Arrays.asList(createOrderDTO(0, "0")
                        , createOrderDTO(1, "1")));


        mockMvc.perform(MockMvcRequestBuilders.get("/orders/dto/{startDate}/{endDate}",
                START_DATE, END_DATE)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].passportNumber", Matchers.is("AB42120")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carNumber", Matchers.is("AC4120")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalTime", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalCost", Matchers.is(70)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalCost", Matchers.is(140)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].passportNumber", Matchers.is("AB42121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carNumber", Matchers.is("AC4121")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalTime", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalCost", Matchers.is(71)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].totalCost", Matchers.is(141)))
                ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE))
                .findDTOsByDate(START_DATE, END_DATE);
    }

    @Test
    void shouldAddNewRentalOrder() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).add(Mockito.any(RentalOrder.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createOrder(1)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).add(Mockito.any(RentalOrder.class));
    }

    @Test
    void shouldUpdateRentalOrder() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).update(Mockito.any(RentalOrder.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createOrder(1)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).update(Mockito.any(RentalOrder.class));
    }

    @Test
    void shouldDeleteRentalOrder() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).delete(Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/order/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createOrder(1)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).delete(Mockito.anyInt());
    }

    private RentalOrder createOrder(int index) {
        RentalOrder order = new RentalOrder();
        order.setOrderId(index);
        order.setClientId(index);
        order.setCarId(index);
        order.setRentalTime(BigDecimal.valueOf(1 + index));
        return order;
    }

    private RentalOrderDTO createOrderDTO(int index, String stringIndex) {
        RentalOrderDTO orderDTO = new RentalOrderDTO();
        orderDTO.setOrderId(index);
        orderDTO.setPassportNumber("AB4212" + stringIndex);
        orderDTO.setCarNumber("AC412" + stringIndex);
        orderDTO.setRentalTime(BigDecimal.valueOf(2 + index));
        orderDTO.setRentalCost(BigDecimal.valueOf(70 + index));
        orderDTO.setTotalCost(BigDecimal.valueOf(140 + index));
        return orderDTO;
    }
}