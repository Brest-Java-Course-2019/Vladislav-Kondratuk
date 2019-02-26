package com.epam.brest.courses.rc.model;

import java.math.BigDecimal;
import java.sql.Date;

public class RentalOrder {


    private Integer orderId;

    private Integer clientId;

    private Integer carId;

    private BigDecimal rentalTime;

    private Date regDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(BigDecimal rentalTime) {
        this.rentalTime = rentalTime;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "RentalOrder{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", carId=" + carId +
                ", rentalTime=" + rentalTime +
                ", regDate=" + regDate +
                '}';
    }
}
