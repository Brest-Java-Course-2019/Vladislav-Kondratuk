package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.filter.ClientDateInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarCostIntervalValidatorTest {

    private CarCostInterval interval;

    private CarCostIntervalValidator intervalValidator = new CarCostIntervalValidator();

    private BindingResult result;

    @BeforeEach
    void setup() {
        interval = Mockito.mock(CarCostInterval.class);
        result = new BeanPropertyBindingResult(interval, "interval");
    }

    @Test
    void shouldRunSupportsMethod() {
        // when
        intervalValidator.supports(CarCostInterval.class);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullStartInterval() {
        // given
        Mockito.when(interval.getCostStartInterval()).thenReturn(null);

        // when
        intervalValidator.validate(interval, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullEndInterval() {
        // given
        Mockito.when(interval.getCostEndInterval()).thenReturn(null);

        // when
        intervalValidator.validate(interval, result);

        // then
        assertTrue(result.hasErrors());
    }
}