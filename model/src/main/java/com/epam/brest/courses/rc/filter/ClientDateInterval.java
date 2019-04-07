package com.epam.brest.courses.rc.filter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.Calendar;

/**
 * Filter class of client's adding date interval.
 */
public class ClientDateInterval {

    /**
     * Minimal date interval.
     */
    private static final String MINIMAL_DATE = "2019-01-01";

    /**
     * The start interval of adding date, for search filter.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date addStartInterval;

    /**
     * The end interval of adding date, for search filter.
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date addEndInterval;

    /**
     * Constructor with date interval parameters.
     *
     * @param addStartInterval start interval of adding date.
     * @param addEndInterval end interval of adding date.
     */
    public ClientDateInterval(String addStartInterval, String addEndInterval) {
        this.addStartInterval = Date.valueOf(addStartInterval);
        this.addEndInterval = Date.valueOf(addEndInterval);
    }

    /**
     * Constructor with default date interval parameters.
     */
    public ClientDateInterval() {
        this.addStartInterval = Date.valueOf(MINIMAL_DATE);
        this.addEndInterval = new Date(Calendar.getInstance().getTime().getTime());
    }

    /**
     * @return Returns the start interval of adding date, for search filter.
     */
    public Date getAddStartInterval() {
        return addStartInterval;
    }

    /**
     * Sets the start interval of adding date, for search filter to the <code>addStartInterval</code>.
     *
     * @param addStartInterval the new start interval of adding date, for search filter.
     */
    public void setAddStartInterval(Date addStartInterval) {
        this.addStartInterval = addStartInterval;
    }

    /**
     * @return Returns the end interval of adding date, for search filter.
     */
    public Date getAddEndInterval() {
        return addEndInterval;
    }

    /**
     * Sets the end interval of adding date, for search filter to the <code>addEndInterval</code>.
     *
     * @param addEndInterval the new end interval of adding date, for search filter.
     */
    public void setAddEndInterval(Date addEndInterval) {
        this.addEndInterval = addEndInterval;
    }

    /**
     * Override toString method.
     *
     * @return string which describes the ClientDateInterval.
     */
    @Override
    public String toString() {
        return "ClientDateInterval{" +
                "addStartInterval=" + addStartInterval +
                ", addEndInterval=" + addEndInterval +
                '}';
    }
}
