package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

class RentalOrderDateIntervalValidatorTest {

    private RentalOrderDateInterval interval;

    private RentalOrderDateIntervalValidator intervalValidator = new RentalOrderDateIntervalValidator();

    private BindingResult result;

    @BeforeEach
    void setup() {
        interval = Mockito.mock(RentalOrderDateInterval.class);
        result = new BeanPropertyBindingResult(interval, "interval");
    }

    @Test
    void shouldRunSupportsMethod() {
        // when
        intervalValidator.supports(RentalOrderDateInterval.class);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullStartInterval() {
        // given
        Mockito.when(interval.getRegStartInterval()).thenReturn(null);

        // when
        intervalValidator.validate(interval, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullEndInterval() {
        // given
        Mockito.when(interval.getRegEndInterval()).thenReturn(null);

        // when
        intervalValidator.validate(interval, result);

        // then
        assertTrue(result.hasErrors());
    }
}