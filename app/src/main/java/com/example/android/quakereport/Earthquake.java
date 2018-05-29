package com.example.android.quakereport;

class Earthquake {
    private String place;
    private long date;
    private double magnitude;
    private String url;

    Earthquake(double magnitude, String place, long date, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public long getDate() {
        return date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getUrl() {
        return url;
    }
}
