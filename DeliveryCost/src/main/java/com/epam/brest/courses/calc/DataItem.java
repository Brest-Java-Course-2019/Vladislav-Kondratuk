package com.epam.brest.courses.calc;

import java.math.BigDecimal;

public class DataItem {

    private BigDecimal costPerKm;
    private BigDecimal costPerKg;
    private BigDecimal weight;
    private BigDecimal distance;

    public void setCostPerKg(BigDecimal costPerKg) {
        this.costPerKg = costPerKg;
    }

    public BigDecimal getCostPerKg() {
        return costPerKg;
    }

    public void setCostPerKm(BigDecimal costPerKm) {
        this.costPerKm = costPerKm;
    }

    public BigDecimal getCostPerKm() {
        return costPerKm;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getDistance() {
        return distance;
    }
}
