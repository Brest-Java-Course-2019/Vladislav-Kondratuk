package com.epam.brest.courses.rc.filter;

import java.math.BigDecimal;

/**
 * Filter class of car rental cost interval.
 */
public class CarCostInterval {

    /**
     * The start interval of rental cost, for search filter.
     */
    private BigDecimal costStartInterval;

    /**
     * The end interval of rental cost, for search filter.
     */
    private BigDecimal costEndInterval;

    /**
     * Constructor with rental cost interval parameters.
     *
     * @param costStartInterval start interval of rental cost.
     * @param costEndInterval end interval of rental cost.
     */
    public CarCostInterval(BigDecimal costStartInterval, BigDecimal costEndInterval) {
        this.costStartInterval = costStartInterval;
        this.costEndInterval = costEndInterval;
    }

    /**
     * @return Returns the start interval of rental cost, for search filter.
     */
    public BigDecimal getCostStartInterval() {
        return costStartInterval;
    }

    /**
     * Sets the start interval of rental cost, for search filter to the <code>costStartInterval</code>.
     *
     * @param costStartInterval the new start interval of rental cost, for search filter.
     */
    public void setCostStartInterval(BigDecimal costStartInterval) {
        this.costStartInterval = costStartInterval;
    }

    /**
     * @return Returns the end interval of rental cost, for search filter.
     */
    public BigDecimal getCostEndInterval() {
        return costEndInterval;
    }

    /**
     * Sets the end interval of rental cost, for search filter to the <code>costEndInterval</code>.
     *
     * @param costEndInterval the new end interval of rental cost, for search filter.
     */
    public void setCostEndInterval(BigDecimal costEndInterval) {
        this.costEndInterval = costEndInterval;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the CarCostInterval.
     */
    @Override
    public String toString() {
        return "CarCostInterval{" +
                "costStartInterval=" + costStartInterval +
                ", costEndInterval=" + costEndInterval +
                '}';
    }
}
