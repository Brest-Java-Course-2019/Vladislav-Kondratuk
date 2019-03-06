package com.epam.brest.courses.rc.model;

import java.math.BigDecimal;

public class Car {

    private Integer carId;

    private String carDescription;

    private String carNumber;

    private BigDecimal rentalCost;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public BigDecimal getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carDescription='" + carDescription + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", rentalCost=" + rentalCost +
                '}';
    }
}
