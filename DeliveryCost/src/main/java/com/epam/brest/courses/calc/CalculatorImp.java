package com.epam.brest.courses.calc;

import java.math.BigDecimal;

public class CalculatorImp implements Calculator {

    @Override
    public BigDecimal calc(DataItem dataItem) {
        return dataItem.getWeight().multiply(dataItem.getCostPerKg())
                .add(dataItem.getDistance().multiply(dataItem.getCostPerKm()));
    }
}
