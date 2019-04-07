package com.epam.brest.courses.rc.web_app.consumers;

//import com.epam.brest.courses.rc.dto.RentalOrderDTO;
//import com.epam.brest.courses.rc.model.RentalOrder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.client.ExpectedCount;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:web-spring-test.xml"})
//class RentalOrderRestConsumerTest {
//
//    private static final int ZERO = 0;
//    private static final int ONE = 1;
//
//    @Autowired
//    private RestTemplate restTemplate = new RestTemplate();
//
//    private MockRestServiceServer mockServer;
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @BeforeEach
//    void init() {
//        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
//    }
//
//    @Test
//    void findAll() throws Exception {
//        List<RentalOrder> orders = Arrays.asList(createOrder(ZERO)
//                , createOrder(ONE));
//
//        mockServer.expect(ExpectedCount.once(),
//                requestTo("http://localhost:8088/orders/all"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(orders))
//                );
//
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//    }
//
//    @Test
//    void findAllDTOs() {
//    }
//
//    @Test
//    void findById() throws Exception {
//
//    }
//
//    @Test
//    void findDTOById() {
//    }
//
//    @Test
//    void findDTOsByDate() {
//    }
//
//    @Test
//    void add() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    private RentalOrder createOrder(int index) {
//        RentalOrder order = new RentalOrder();
//        order.setOrderId(index);
//        order.setClientId(index);
//        order.setCarId(index);
//        order.setRentalTime(BigDecimal.valueOf(1 + index));
//        return order;
//    }
//
//    private RentalOrderDTO createOrderDTO(int index) {
//        RentalOrderDTO orderDTO = new RentalOrderDTO();
//        orderDTO.setOrderId(index);
//        orderDTO.setPassportNumber("AB4212" + index);
//        orderDTO.setCarNumber("AC412" + index);
//        orderDTO.setRentalTime(BigDecimal.valueOf(2 + index));
//        orderDTO.setRentalCost(BigDecimal.valueOf(70 + index));
//        orderDTO.setTotalCost(BigDecimal.valueOf(140 + index));
//        return orderDTO;
//    }
//}