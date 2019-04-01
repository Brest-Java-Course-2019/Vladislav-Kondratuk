package com.epam.brest.courses.rc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Model class of rental order.
 */
public class RentalOrder {

    /**
     * The rental order ID.
     */
    private Integer orderId;

    /**
     * ID of the client who signed the rental order.
     */
    private Integer clientId;

    /**
     * ID of the car on which the rental order is signed.
     */
    private Integer carId;

    /**
     * The rental time.
     */
    private BigDecimal rentalTime;

    /**
     * The rental order registration date.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date regDate;

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
     * @return Returns the client's ID.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets the client's ID to the <code>clientId</code>.
     *
     * @param clientId the new client's ID.
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

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
     * Override toString method.
     *
     * @return string which describes the RentalOrder.
     */
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
