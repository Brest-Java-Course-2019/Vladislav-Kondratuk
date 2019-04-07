package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
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

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/root-context.xml", "classpath:web-spring-test.xml"})
class RentalOrderControllerTest {

    private static final int ONCE = 1;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private RentalOrderService rentalOrderService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldGetOrdersPage() throws Exception {
        Mockito.when(rentalOrderService.findAllDTOs()).thenReturn(Arrays.asList(createOrderDTO(ZERO)
                , createOrderDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("orders"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Orders list</title>")))
        ;
        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldGetAddOrderPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/add-order")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers
                        .containsString("<title>Add order</title>")))
        ;
    }

    @Test
    void shouldGetEditOrderPage() throws Exception {
        Mockito.when(rentalOrderService.findById(Mockito.anyInt())).thenReturn(createOrder(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/edit-order/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<title>Edit order</title>")))
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldAddRentalOrder() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).add(createOrder(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("carId", "1")
                        .param("rentalTime", "2")
                        .param("regDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders"))
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).add(Mockito.any(RentalOrder.class));
    }

    @Test
    void shouldUpdateRentalOrder() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).update(createOrder(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .param("carId", "1")
                        .param("rentalTime", "2")
                        .param("regDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders"))
        ;

        Mockito.verify(rentalOrderService, Mockito.times(ONCE)).update(Mockito.any(RentalOrder.class));
    }

    @Test
    void shouldGetAddRentalOrderValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).add(createOrder(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("rentalTime", "2")
                        .param("regDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clientId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldGetUpdateRentalOrderValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).update(createOrder(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("rentalTime", "2")
                        .param("regDate", "2000-01-01")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clientId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldDeleteRentalOrderById() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(rentalOrderService).delete(Mockito.anyInt());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/order/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/orders"))
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

    private RentalOrderDTO createOrderDTO(int index) {
        RentalOrderDTO orderDTO = new RentalOrderDTO();
        orderDTO.setOrderId(index);
        orderDTO.setPassportNumber("AB4212" + index);
        orderDTO.setCarNumber("AC412" + index);
        orderDTO.setRentalTime(BigDecimal.valueOf(2 + index));
        orderDTO.setRentalCost(BigDecimal.valueOf(70 + index));
        orderDTO.setTotalCost(BigDecimal.valueOf(140 + index));
        return orderDTO;
    }
}