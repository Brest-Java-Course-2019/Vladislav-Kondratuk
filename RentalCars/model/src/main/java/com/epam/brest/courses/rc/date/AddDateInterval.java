package com.epam.brest.courses.rc.date;

import java.sql.Date;

public class AddDateInterval {

    private Date addStartInterval;

    private Date addEndInterval;

    public AddDateInterval(String addStartInterval, String addEndInterval) {
        this.addStartInterval = Date.valueOf(addStartInterval);
        this.addEndInterval = Date.valueOf(addEndInterval);
    }

    public Date getAddStartInterval() {
        return addStartInterval;
    }

    public void setAddStartInterval(Date addStartInterval) {
        this.addStartInterval = addStartInterval;
    }

    public Date getAddEndInterval() {
        return addEndInterval;
    }

    public void setAddEndInterval(Date addEndInterval) {
        this.addEndInterval = addEndInterval;
    }

    @Override
    public String toString() {
        return "AddDateInterval{" +
                "addStartInterval=" + addStartInterval +
                ", addEndInterval=" + addEndInterval +
                '}';
    }
}

