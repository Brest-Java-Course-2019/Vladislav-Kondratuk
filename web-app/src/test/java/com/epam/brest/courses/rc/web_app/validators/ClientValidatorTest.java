package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {

    private Client client;

    private ClientValidator clientValidator = new ClientValidator();

    private BindingResult result;

    @BeforeEach
    void setup() {
        client = Mockito.mock(Client.class);
        result = new BeanPropertyBindingResult(client, "client");
    }

    @Test
    void shouldRunSupportsMethod() {
        // when
        clientValidator.supports(Client.class);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullPassportNumber() {
        // given
        Mockito.when(client.getPassportNumber()).thenReturn(null);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyPassportNumber() {
        // given
        Mockito.when(client.getPassportNumber()).thenReturn("");

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargePassportNumber() {

        // given
        String filled = StringUtils.repeat("*", 35);
        Mockito.when(client.getPassportNumber()).thenReturn(filled);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullFirstName() {
        // given
        Mockito.when(client.getFirstName()).thenReturn(null);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyFirstName() {
        // given
        Mockito.when(client.getFirstName()).thenReturn("");

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeFirstName() {

        // given
        String filled = StringUtils.repeat("*", 57);
        Mockito.when(client.getFirstName()).thenReturn(filled);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullLastName() {
        // given
        Mockito.when(client.getLastName()).thenReturn(null);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyLastName() {
        // given
        Mockito.when(client.getLastName()).thenReturn("");

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeLastName() {

        // given
        String filled = StringUtils.repeat("*", 57);
        Mockito.when(client.getLastName()).thenReturn(filled);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullRegDate() {
        // given
        Mockito.when(client.getAddDate()).thenReturn(null);

        // when
        clientValidator.validate(client, result);

        // then
        assertTrue(result.hasErrors());
    }

}