package com.epam.brest.courses.rc.filter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Filter class of car rental cost interval.
 */
public class CarCostInterval {

    private static final BigDecimal DEFAULT_COST = BigDecimal.valueOf(0).setScale(2, RoundingMode.CEILING);
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
     * Constructor with default cost interval parameters.
     */
    public CarCostInterval() {
        this.costStartInterval = DEFAULT_COST;
        this.costEndInterval = DEFAULT_COST;
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
