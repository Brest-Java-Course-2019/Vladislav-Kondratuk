package com.epam.brest.courses.calc;

import java.math.BigDecimal;

public class CalculatorImp implements Calculator {

    @Override //проверка переопределение базового класса
    public BigDecimal calc(DataItem dataItem) {
        return dataItem.getWeight().multiply(dataItem.getCostPerKg()).add(dataItem.getDistance().multiply(dataItem.getCostPerKm()));
    }
}
