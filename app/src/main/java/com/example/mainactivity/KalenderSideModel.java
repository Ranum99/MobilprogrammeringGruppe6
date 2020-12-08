package com.example.mainactivity;

public class KalenderSideModel {
    private String dateFrom, dateTo, timeFrom, timeTo, userName, theActivity;

    public KalenderSideModel(String dateFrom, String dateTo, String timeFrom, String timeTo, String userName, String theActivity) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.userName = userName;
        this.theActivity = theActivity;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getUserName() {
        return userName;
    }

    public String getTheActivity() {
        return theActivity;
    }
}
