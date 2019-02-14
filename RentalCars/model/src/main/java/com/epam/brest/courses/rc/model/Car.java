package com.epam.brest.courses.rc.model;

import java.math.BigDecimal;

public class Car {

    private Integer carId;

    private String carName;

    private Integer yearIssue;

    private String bodyType;

    private String transmission;

    private Integer orderId;

    private BigDecimal rentalCost;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getYearIssue() {
        return yearIssue;
    }

    public void setYearIssue(Integer yearIssue) {
        this.yearIssue = yearIssue;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
                ", carName='" + carName + '\'' +
                ", yearIssue=" + yearIssue +
                ", bodyType='" + bodyType + '\'' +
                ", transmission='" + transmission + '\'' +
                ", orderId=" + orderId +
                ", rentalCost=" + rentalCost +
                '}';
    }
}
