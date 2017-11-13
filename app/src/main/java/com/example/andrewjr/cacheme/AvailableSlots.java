package com.example.andrewjr.cacheme;

import android.util.Log;

/**
 * Created by andrewjr on 11/12/17.
 */

public class AvailableSlots {
    private String available;
    private String lat;
    private String longi;
    private String pricePerHour;
    private String ownerUsername;

    public Boolean getAvailable() {
        return available.equals("true") ? true: false;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Double getLat() {
        return Double.parseDouble(lat);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return Double.parseDouble(longi);
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }


    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
