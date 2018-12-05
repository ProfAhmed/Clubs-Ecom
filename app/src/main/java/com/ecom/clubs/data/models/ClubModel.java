package com.ecom.clubs.data.models;

import com.google.android.gms.maps.model.LatLng;

public class ClubModel {
    String name;
    LatLng latLng;

    public ClubModel(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "ClubModel{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
