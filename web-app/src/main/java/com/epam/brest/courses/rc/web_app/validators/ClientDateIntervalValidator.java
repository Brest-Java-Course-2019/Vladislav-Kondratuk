package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.filter.ClientDateInterval;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClientDateIntervalValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ClientDateInterval.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "addStartInterval", "addStartInterval.empty");
        ValidationUtils.rejectIfEmpty(errors, "addEndInterval", "addEndInterval.empty");
    }

}
