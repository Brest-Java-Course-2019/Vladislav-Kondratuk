package com.epam.brest.courses.rc.model;

import java.math.BigDecimal;

public class Car {

    /**
     * The car ID.
     */
    private Integer carId;

    /**
     * The car description.
     */
    private String carDescription;

    /**
     * The car number.
     */
    private String carNumber;

    /**
     * The car rental cost.
     */
    private BigDecimal rentalCost;

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
     * @return Returns the car number.
     */
    public String getCarNumber() {
        return carNumber;
    }

    /**
     * Sets the car number to the <code>carNumber</code>.
     *
     * @param carNumber the new car number.
     */
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
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
     * Override toString method.
     *
     * @return string which describes the Car.
     */
    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carDescription='" + carDescription + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", rentalCost=" + rentalCost +
                '}';
    }
}
