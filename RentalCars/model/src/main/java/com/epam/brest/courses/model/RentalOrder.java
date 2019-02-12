package com.epam.brest.courses.model;

import java.math.BigDecimal;

public class RentalOrder {

    private Integer orderId;

    private Integer orderNumber;

    private BigDecimal rentalTime;

    private BigDecimal totalCost;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(BigDecimal rentalTime) {
        this.rentalTime = rentalTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
