package com.epam.brest.courses.rc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * DTO class of rental order.
 */
public class RentalOrderDTO {

    /**
     * The rental order ID.
     */
    private Integer orderId;

    /**
     * The client's passport number.
     */
    private String passportNumber;

    /**
     * The car number.
     */
    private String carNumber;

    /**
     * The rental order registration date.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date regDate;

    /**
     * The car rental time.
     */
    private BigDecimal rentalTime;

    /**
     * The car rental cost.
     */
    private BigDecimal rentalCost;

    /**
     * The rental order total cost.
     */
    private BigDecimal totalCost;

    /**
     * @return Returns the rental order ID.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets the rental order ID to the <code>orderId</code>.
     *
     * @param orderId the new rental order ID.
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * @return Returns the client's passport number.
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Sets the client's passport number to the <code>passportNumber</code>.
     *
     * @param passportNumber the new client's passport number.
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
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
     * @return Returns the rental order registration date.
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * Sets the rental order registration date to the <code>regDate</code>.
     *
     * @param regDate the new rental order registration date.
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @return Returns the rental time.
     */
    public BigDecimal getRentalTime() {
        return rentalTime;
    }

    /**
     * Sets the rental time to the <code>rentalTime</code>.
     *
     * @param rentalTime the new rental time.
     */
    public void setRentalTime(BigDecimal rentalTime) {
        this.rentalTime = rentalTime;
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
     * @return Returns the rental order total cost.
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the rental order total cost to the <code>totalCost</code>.
     *
     * @param totalCost the new rental order total cost.
     */
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the RentalOrderDTO.
     */
    @Override
    public String toString() {
        return "RentalOrderDTO{" +
                "orderId=" + orderId +
                ", passportNumber='" + passportNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", regDate=" + regDate +
                ", rentalTime=" + rentalTime +
                ", rentalCost=" + rentalCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
