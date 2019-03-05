package com.epam.brest.courses.rc.model;

import java.sql.Date;

public class RegDateInterval {

    private Date regStartInterval;

    private Date regEndInterval;

    public RegDateInterval(String strStartRegDate, String strEndRegDate) {
        this.regStartInterval = Date.valueOf(strStartRegDate);
        this.regEndInterval = Date.valueOf(strEndRegDate);
    }

    public Date getRegStartInterval() {
        return regStartInterval;
    }

    public void setRegStartInterval(Date regStartInterval) {
        this.regStartInterval = regStartInterval;
    }

    public Date getRegEndInterval() {
        return regEndInterval;
    }

    public void setRegEndInterval(Date regEndInterval) {
        this.regEndInterval = regEndInterval;
    }

    @Override
    public String toString() {
        return "RegDateInterval{" +
                "regStartInterval=" + regStartInterval +
                ", regEndInterval=" + regEndInterval +
                '}';
    }
}
