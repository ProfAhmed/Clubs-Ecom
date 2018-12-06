package com.ecom.clubs.data.models;

import com.google.android.gms.maps.model.LatLng;

public class ClubRequestModel {
    String governmentName, city, gameName;
    LatLng latLng;
    double distance;

    public ClubRequestModel(String governmentName, String city, java.lang.String gameName, LatLng latLng, double distance) {
        this.governmentName = governmentName;
        this.city = city;
        this.gameName = gameName;
        this.latLng = latLng;
        this.distance = distance;
    }

    public String getGovernmentName() {
        return governmentName;
    }

    public String getString() {
        return city;
    }

    public String getGameName() {
        return gameName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public double getDistance() {
        return distance;
    }
}
