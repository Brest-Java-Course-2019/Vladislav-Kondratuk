package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.service.CarService;
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
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/root-context.xml", "classpath:web-spring-test.xml"})
class CarControllerTest {

    private static final BigDecimal START_COST = BigDecimal.valueOf(70);
    private static final BigDecimal END_COST = BigDecimal.valueOf(80);

    private static final int ONCE = 1;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldGetCarsPage() throws Exception {
        Mockito.when(carService.findAllDTOs()).thenReturn(Arrays.asList(createCarDTO(ZERO)
                , createCarDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("cars"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Cars list</title>")))
        ;
        Mockito.verify(carService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldGetAddCarPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/add-car")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers
                        .containsString("<title>Add car</title>")))
        ;
    }

    @Test
    void shouldGetEditCarPage() throws Exception {
        Mockito.when(carService.findById(Mockito.anyInt())).thenReturn(createCar(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/edit-car/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<title>Edit car</title>")))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldAddCar() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).add(createCar(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("carDescription", "bmw")
                        .param("carNumber", "BY41241")
                        .param("rentalCost", "87.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cars"))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).add(Mockito.any(Car.class));
    }

    @Test
    void shouldUpdateCar() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).update(createCar(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("carDescription", "bmw")
                        .param("carNumber", "BY41241")
                        .param("rentalCost", "87.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cars"))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).update(Mockito.any(Car.class));
    }

    @Test
    void shouldGetAddCarValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).add(createCar(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("carDescription", "bmw")
                        .param("rentalCost", "87.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("carNumber"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldGetUpdateCarValidationError() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).update(createCar(ZERO));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit-car/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("carId", "1")
                        .param("carDescription", "bmw")
                        .param("rentalCost", "87.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("carNumber"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void shouldDeleteCarById() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).delete(Mockito.anyInt());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/car/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cars"))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).delete(Mockito.anyInt());
    }

    @Test
    void shouldGetFilterOrdersValidationErrorPage() throws Exception {
        Mockito.when(carService.findAllDTOs())
                .thenReturn(Arrays.asList(createCarDTO(ZERO), createCarDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/filter-cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("costStartInterval", "")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("costStartInterval"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("costEndInterval"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("cars"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Cars list</title>")))
        ;
    }

    @Test
    void shouldGetFilterOrdersPage() throws Exception {
        Mockito.when(carService.findDTOsByCost(START_COST, END_COST))
                .thenReturn(Collections.singletonList(createCarDTO(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/filter-cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("costStartInterval", "70")
                        .param("costEndInterval", "80")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("cars"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>Cars list</title>")))
        ;
    }

    private Car createCar(int index) {
        Car car = new Car();
        car.setCarId(index);
        car.setCarDescription("bmw" + index);
        car.setCarNumber("AC4321" + index);
        car.setRentalCost(BigDecimal.valueOf(50 + index));
        return car;
    }

    private CarDTO createCarDTO(int index) {
        CarDTO carDTO = new CarDTO();
        carDTO.setCarId(index);
        carDTO.setCarDescription("bmw" + index);
        carDTO.setRentalCost(BigDecimal.valueOf(50 + index));
        carDTO.setNumberOfOrders(index);
        return carDTO;
    }
}