package com.epam.brest.courses.rc.web_app.consumers;

//import com.epam.brest.courses.rc.dto.RentalOrderDTO;
//import com.epam.brest.courses.rc.model.RentalOrder;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
//class RentalOrderRestConsumerTest {
//
//    private static final int ZERO = 0;
//    private static final int ONE = 1;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private RentalOrderRestConsumer rentalOrderService;
//
//    @Test
//    @SuppressWarnings("unchecked")
//    void shouldGetAllRentalOrders() {
//        List<RentalOrder> orderList = Arrays.asList(createOrder(ZERO), createOrder(ONE));
//        ResponseEntity responseEntity = new ResponseEntity(orderList, HttpStatus.OK);
//
//        Mockito.when(restTemplate.getForEntity("http://localhost:8088/orders",
//                RentalOrder.class)).thenReturn(responseEntity);
//        List<RentalOrder> orderList1 = rentalOrderService.findAll();
//        assertNotNull(orderList1);
//        assertEquals(orderList1.size(),2);
//    }
//
//    @Test
//    void findAll() throws Exception {
//
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