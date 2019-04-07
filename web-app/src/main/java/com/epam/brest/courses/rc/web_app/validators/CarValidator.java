package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CarValidator implements Validator {


    private static final int CAR_NUMBER_MAX_SIZE = 30;
    private static final int CAR_DESCRIPTION_MAX_SIZE = 90;

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "carDescription", "carDescription.empty");
        ValidationUtils.rejectIfEmpty(errors, "carNumber", "carNumber.empty");
        ValidationUtils.rejectIfEmpty(errors, "rentalCost", "rentalCost.empty");

        Car car = (Car) obj;
        if (StringUtils.hasLength(car.getCarDescription())
                && car.getCarDescription().length() > CAR_DESCRIPTION_MAX_SIZE) {
            errors.rejectValue("carDescription", "carDescription.maxSize");
        }
        if (StringUtils.hasLength(car.getCarNumber())
                && car.getCarNumber().length() > CAR_NUMBER_MAX_SIZE) {
            errors.rejectValue("carNumber", "carNumber.maxSize");
        }
        if (car.getRentalCost() != null && car.getRentalCost().floatValue() < 0){
            errors.rejectValue("rentalCost", "rentalCost.negative");
        }
    }
}
