package com.epam.brest.courses.rc.dto;

import java.math.BigDecimal;

/**
 * DTO class of car.
 */
public class CarDTO {

    /**
     * The car ID.
     */
    private Integer carId;

    /**
     * The car description.
     */
    private String carDescription;

    /**
     * The car rental cost.
     */
    private BigDecimal rentalCost;

    /**
     * The car number of orders.
     */
    private Integer numberOfOrders;

    /**
     * @return Returns the car ID.
     */
    public Integer getCarId() {
        return carId;
    }

    /**
     * Sets the car ID to the <code>carId</code>.
     *
     * @param carId the new car ID.
     */
    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    /**
     * @return Returns the car description.
     */
    public String getCarDescription() {
        return carDescription;
    }

    /**
     * Sets the car description to the <code>carDescription</code>.
     *
     * @param carDescription the new car description.
     */
    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    /**
     * @return Returns the car rental cost.
     */
    public BigDecimal getRentalCost() {
        return rentalCost;
    }


    /**
     * Sets the car rental cost to the <code>rentalCost</code>.
     *
     * @param rentalCost the new car rental cost.
     */
    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }

    /**
     * @return Returns the car number of orders.
     */
    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    /**
     * Sets the car number of orders to the <code>numberOfOrders</code>.
     *
     * @param numberOfOrders the new car number of orders.
     */
    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the CarDTO.
     */
    @Override
    public String toString() {
        return "CarDTO{" +
                "carId=" + carId +
                ", carDescription='" + carDescription + '\'' +
                ", rentalCost=" + rentalCost +
                ", numberOfOrders=" + numberOfOrders +
                '}';
    }
}
