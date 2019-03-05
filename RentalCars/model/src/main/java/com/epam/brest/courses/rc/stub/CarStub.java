package com.epam.brest.courses.rc.stub;

import java.math.BigDecimal;

public class CarStub {

    private Integer carId;

    private String carDescription;

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

    public BigDecimal getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }

    @Override
    public String toString() {
        return "CarStub{" +
                "carId=" + carId +
                ", carDescription='" + carDescription + '\'' +
                ", rentalCost=" + rentalCost +
                '}';
    }
}
