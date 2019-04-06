package com.epam.brest.courses.rc.web_app.validators;

import com.epam.brest.courses.rc.model.RentalOrder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RentalOrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return RentalOrder.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "clientId", "clientId.empty");
        ValidationUtils.rejectIfEmpty(errors, "carId", "carId.empty");
        ValidationUtils.rejectIfEmpty(errors, "rentalTime", "rentalTime.empty");
        ValidationUtils.rejectIfEmpty(errors, "regDate", "regDate.empty");

        RentalOrder order = (RentalOrder) obj;
        if (order.getClientId() != null && order.getClientId() < 0)  {
            errors.rejectValue("clientId", "clientId.negative");
        }
        if (order.getCarId() != null && order.getCarId() < 0)  {
            errors.rejectValue("carId", "carId.negative");
        }
        if (order.getRentalTime() != null && order.getRentalTime().intValue() < 1){
            errors.rejectValue("rentalTime", "rentalTime.negative");
        }
    }

}
