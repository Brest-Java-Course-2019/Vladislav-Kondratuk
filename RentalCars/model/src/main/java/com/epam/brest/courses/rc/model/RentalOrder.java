package com.epam.brest.courses.rc.model;

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

    @Override
    public String toString() {
        return "RentalOrder{" +
                "orderId=" + orderId +
                ", orderNumber=" + orderNumber +
                ", rentalTime=" + rentalTime +
                ", totalCost=" + totalCost +
                '}';
    }
}
