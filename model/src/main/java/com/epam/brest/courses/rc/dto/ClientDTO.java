package com.epam.brest.courses.rc.dto;

import java.sql.Date;

/**
 * DTO class of client.
 */
public class ClientDTO {

    /**
     * The client's ID.
     */
    private Integer clientId;

    /**
     * The client's first name.
     */
    private String firstName;

    /**
     * The client's last name.
     */
    private String lastName;

    /**
     * The client's adding date.
     */
    private Date addDate;

    /**
     * The client's number of orders.
     */
    private Integer numberOfOrders;

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
     * @return Returns the client's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the client's first name to the <code>firstName</code>.
     *
     * @param firstName the new client's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Returns the client's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the client's last name to the <code>lastName</code>.
     *
     * @param lastName the new client's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Returns the client's adding date.
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * Sets the client's adding date to the <code>addDate</code>.
     *
     * @param addDate the new client's adding date.
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    /**
     *
     * @return Returns the client's number of orders.
     */
    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    /**
     * Sets the client's number of orders to the <code>numberOfOrders</code>.
     *
     * @param numberOfOrders the new client's number of orders.
     */
    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the ClientDTO.
     */
    @Override
    public String toString() {
        return "ClientDTO{" +
                "clientId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addDate=" + addDate +
                ", numberOfOrders=" + numberOfOrders +
                '}';
    }
}
