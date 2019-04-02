package com.epam.brest.courses.rc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

/**
 * Model class of client.
 */
public class Client {

    /**
     * The client's ID.
     */
    private Integer clientId;

    /**
     * The client's passport number.
     */
    private String passportNumber;

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
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date addDate;

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
     * Override toString method.
     *
     * @return string which describes the Client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", passportNumber='" + passportNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addDate=" + addDate +
                '}';
    }
}
