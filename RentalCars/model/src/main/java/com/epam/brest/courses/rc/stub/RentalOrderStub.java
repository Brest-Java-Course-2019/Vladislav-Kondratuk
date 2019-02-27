package com.epam.brest.courses.rc.stub;

import java.math.BigDecimal;
import java.sql.Date;

public class RentalOrderStub {

    private Integer orderId;

    private String passportNumber;

    private String carNumber;

    private BigDecimal rentalTime;

    private BigDecimal totalCost;

    private Date regDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public RentalOrderStub orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public RentalOrderStub passportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public RentalOrderStub carNumber(String carNumber) {
        this.carNumber = carNumber;
        return this;
    }

    public BigDecimal getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(BigDecimal rentalTime) {
        this.rentalTime = rentalTime;
    }

    public RentalOrderStub rentalTime(BigDecimal rentalTime) {
        this.rentalTime = rentalTime;
        return this;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public RentalOrderStub totalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public RentalOrderStub regDate(Date regDate) {
        this.regDate = regDate;
        return this;
    }

    @Override
    public String toString() {
        return "RentalOrderStub{" +
                "orderId=" + orderId +
                ", passportNumber='" + passportNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", rentalTime=" + rentalTime +
                ", totalCost=" + totalCost +
                ", regDate=" + regDate +
                '}';
    }
}
