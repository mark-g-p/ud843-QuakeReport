package com.example.android.quakereport;

public class Earthquake {
    private double magnitude;
    String place;
    long date;
    private String url;

    public Earthquake(double magnitude, String place, long date, String url) {
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
