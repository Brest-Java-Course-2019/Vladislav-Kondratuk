package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.filter.CarCostInterval;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CarCostIntervalValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CarCostInterval.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "costStartInterval", "costStartInterval.empty");
        ValidationUtils.rejectIfEmpty(errors, "costEndInterval", "costEndInterval.empty");
    }

}
