package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.CarDTO;
import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.service.CarService;
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
class CarRestControllerTest {

    private static final int ONCE = 1;
    private static final BigDecimal START_COST = BigDecimal.valueOf(60);
    private static final BigDecimal END_COST = BigDecimal.valueOf(80);
    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Autowired
    private CarRestController controller;

    @Autowired
    private CarService carService;

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
        Mockito.verifyNoMoreInteractions(carService);
        Mockito.reset(carService);
    }

    @Test
    void shouldFindAllCars() throws Exception {
        Mockito.when(carService.findAll()).thenReturn(Arrays.asList(createCar(ZERO)
                , createCar(ONE)));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/all")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carId", Matchers.is(ZERO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carDescription", Matchers.is("bmw0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carNumber", Matchers.is("AC43210")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalCost", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carId", Matchers.is(ONE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carDescription", Matchers.is("bmw1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carNumber", Matchers.is("AC43211")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalCost", Matchers.is(51)))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindAllCarDTOs() throws Exception {
        Mockito.when(carService.findAllDTOs()).thenReturn(Arrays.asList(createCarDTO(ZERO)
                , createCarDTO(ONE)));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/all-dto")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carId", Matchers.is(ZERO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carDescription", Matchers.is("bmw0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalCost", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfOrders", Matchers.is(ZERO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carId", Matchers.is(ONE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carDescription", Matchers.is("bmw1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalCost", Matchers.is(51)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfOrders", Matchers.is(ONE)))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findAllDTOs();
    }

    @Test
    void shouldFindCarById() throws Exception {
        Mockito.when(carService.findById(Mockito.anyInt()))
                .thenReturn(createCar(ONE));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/car/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId", Matchers.is(ONE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carDescription", Matchers.is("bmw1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carNumber", Matchers.is("AC43211")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalCost", Matchers.is(51)))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findById(Mockito.anyInt());
    }

    @Test
    void shouldFindCarDTOById() throws Exception {
        Mockito.when(carService.findDTOById(Mockito.anyInt()))
                .thenReturn(createCarDTO(ONE));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/dto/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId", Matchers.is(ONE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carDescription", Matchers.is("bmw1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalCost", Matchers.is(51)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfOrders", Matchers.is(ONE)))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findDTOById(Mockito.anyInt());
    }

    @Test
    void shouldFindCarDTOsByCost() throws Exception {
        Mockito.when(carService.findDTOsByCost(START_COST, END_COST))
                .thenReturn(Arrays.asList(createCarDTO(ZERO), createCarDTO(ONE)));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/dto/{startCost}/{endCost}",
                START_COST, END_COST)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carId", Matchers.is(ZERO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].carDescription", Matchers.is("bmw0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rentalCost", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOfOrders", Matchers.is(ZERO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carId", Matchers.is(ONE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].carDescription", Matchers.is("bmw1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rentalCost", Matchers.is(51)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOfOrders", Matchers.is(ONE)))
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).findDTOsByCost(START_COST, END_COST);
    }

    @Test
    void shouldAddNewCar() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).add(Mockito.any(Car.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createCar(ONE)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).add(Mockito.any(Car.class));
    }

    @Test
    void shouldUpdateCar() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).update(Mockito.any(Car.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createCar(ONE)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).update(Mockito.any(Car.class));
    }

    @Test
    void shouldDeleteCar() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(carService).delete(Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/car/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(createCar(ONE)))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
        ;

        Mockito.verify(carService, Mockito.times(ONCE)).delete(Mockito.anyInt());
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