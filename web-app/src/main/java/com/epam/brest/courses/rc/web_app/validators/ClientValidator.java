package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.Client;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {

    private static final int PASSPORT_NUMBER_MAX_SIZE = 30;
    private static final int FIRST_NAME_MAX_SIZE = 45;
    private static final int LAST_NAME_MAX_SIZE = 45;

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "passportNumber", "passportNumber.empty");
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");
        ValidationUtils.rejectIfEmpty(errors, "addDate", "addDate.empty");

        Client client = (Client) obj;
        if (StringUtils.hasLength(client.getPassportNumber())
                && client.getPassportNumber().length() > PASSPORT_NUMBER_MAX_SIZE) {
            errors.rejectValue("passportNumber", "passportNumber.maxSize");
        }
        if (StringUtils.hasLength(client.getFirstName())
                && client.getFirstName().length() > FIRST_NAME_MAX_SIZE) {
            errors.rejectValue("firstName", "firstName.maxSize");
        }
        if (StringUtils.hasLength(client.getLastName())
                && client.getLastName().length() > LAST_NAME_MAX_SIZE) {
            errors.rejectValue("lastName", "lastName.maxSize");
        }
    }
}
