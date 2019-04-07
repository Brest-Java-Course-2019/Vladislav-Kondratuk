package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarValidatorTest {

    private static final int NEGATIVE_NUMBER = -1;

    private Car car;

    private CarValidator carValidator = new CarValidator();

    private BindingResult result;

    @BeforeEach
    void setup() {
        car = Mockito.mock(Car.class);
        result = new BeanPropertyBindingResult(car, "car");
    }

    @Test
    void shouldRunSupportsMethod() {
        // when
        carValidator.supports(Client.class);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullCarDescription() {
        // given
        Mockito.when(car.getCarDescription()).thenReturn(null);

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCarDescription() {
        // given
        Mockito.when(car.getCarDescription()).thenReturn("");

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeCarDescription() {

        // given
        String filled = StringUtils.repeat("*", 97);
        Mockito.when(car.getCarDescription()).thenReturn(filled);

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullCarNumber() {
        // given
        Mockito.when(car.getCarNumber()).thenReturn(null);

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCarNumber() {
        // given
        Mockito.when(car.getCarNumber()).thenReturn("");

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeCarNumber() {

        // given
        String filled = StringUtils.repeat("*", 57);
        Mockito.when(car.getCarNumber()).thenReturn(filled);

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNegativeRentalCost() {
        // given
        Mockito.when(car.getRentalCost()).thenReturn(BigDecimal.valueOf(NEGATIVE_NUMBER));

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullRentalCost() {
        // given
        Mockito.when(car.getRentalCost()).thenReturn(null);

        // when
        carValidator.validate(car, result);

        // then
        assertTrue(result.hasErrors());
    }

}