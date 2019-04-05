package com.epam.brest.courses.rc.filter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.Calendar;

/**
 * Filter class of rental order registration date interval.
 */
public class RentalOrderDateInterval {

    private static final String MINIMAL_DATE = "2019-01-01";

    /**
     * The start interval of registration date, for search filter.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date regStartInterval;

    /**
     * The end interval of registration date, for search filter.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date regEndInterval;

    /**
     * Constructor with date interval parameters.
     *
     * @param regStartInterval start interval of registration date.
     * @param regEndInterval end interval of registration date.
     */
    public RentalOrderDateInterval(String regStartInterval, String regEndInterval) {
        this.regStartInterval = Date.valueOf(regStartInterval);
        this.regEndInterval = Date.valueOf(regEndInterval);
    }

    public RentalOrderDateInterval() {
        this.regStartInterval = Date.valueOf(MINIMAL_DATE);
        this.regEndInterval = new Date(Calendar.getInstance().getTime().getTime());
    }

    /**
     * @return Returns the start interval of registration date, for search filter.
     */
    public Date getRegStartInterval() {
        return regStartInterval;
    }

    /**
     * Sets the start interval of registration date, for search filter to the <code>regStartInterval</code>.
     *
     * @param regStartInterval the new start interval of registration date, for search filter.
     */
    public void setRegStartInterval(Date regStartInterval) {
        this.regStartInterval = regStartInterval;
    }

    /**
     * @return Returns the end interval of registration date, for search filter.
     */
    public Date getRegEndInterval() {
        return regEndInterval;
    }

    /**
     * Sets the end interval of registration date, for search filter to the <code>regEndInterval</code>.
     *
     * @param regEndInterval the new end interval of registration date, for search filter.
     */
    public void setRegEndInterval(Date regEndInterval) {
        this.regEndInterval = regEndInterval;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the RentalOrderDateInterval.
     */
    @Override
    public String toString() {
        return "RentalOrderDateInterval{" +
                "regStartInterval=" + regStartInterval +
                ", regEndInterval=" + regEndInterval +
                '}';
    }
}
