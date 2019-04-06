package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RentalOrderValidatorTest {

    private static final int NEGATIVE_NUMBER = -1;

    private RentalOrder order;

    private RentalOrderValidator orderValidator = new RentalOrderValidator();

    private BindingResult result;

    @BeforeEach
    void setup() {
        order = Mockito.mock(RentalOrder.class);
        result = new BeanPropertyBindingResult(order, "order");
    }

    @Test
    void shouldRunSupportsMethod() {
        // when
        orderValidator.supports(RentalOrder.class);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNegativeClientId() {
        // given
        Mockito.when(order.getClientId()).thenReturn(NEGATIVE_NUMBER);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullClientId() {
        // given
        Mockito.when(order.getClientId()).thenReturn(null);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNegativeCarId() {
        // given
        Mockito.when(order.getCarId()).thenReturn(NEGATIVE_NUMBER);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullCarId() {
        // given
        Mockito.when(order.getCarId()).thenReturn(null);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNegativeRentalTime() {
        // given
        Mockito.when(order.getRentalTime()).thenReturn(BigDecimal.valueOf(NEGATIVE_NUMBER));

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullRentalTime() {
        // given
        Mockito.when(order.getRentalTime()).thenReturn(null);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullRegDate() {
        // given
        Mockito.when(order.getRegDate()).thenReturn(null);

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }
}