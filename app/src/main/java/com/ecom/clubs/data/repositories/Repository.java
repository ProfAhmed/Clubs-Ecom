package com.ecom.clubs.data.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;

import com.ecom.clubs.constants.Constants;
import com.ecom.clubs.data.models.ClubModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Repository {

    public LiveData<String[]> getGovernments() {
        MutableLiveData<String[]> data = new MutableLiveData<>();
        data.setValue(Constants.governments);
        return data;
    }

    public LiveData<String[]> getCities(String governmentName) {
        MutableLiveData<String[]> data = new MutableLiveData<>();
        if (governmentName.equals(Constants.governments[0]))
            data.setValue(Constants.citiesCairo);
        else {
            data.setValue(Constants.citiesAlex);
        }
        return data;
    }

    public LiveData<String[]> getGames() {
        MutableLiveData<String[]> data = new MutableLiveData<>();
        data.setValue(Constants.gameName);
        return data;
    }

    public LiveData<ArrayList<ClubModel>> getClubs(String governmentName, String gameName, LatLng latLng, double distance) {
        MutableLiveData<ArrayList<ClubModel>> data = new MutableLiveData<>();
        ArrayList<ClubModel> allClubs = null;
        ArrayList<ClubModel> clubs = new ArrayList<>();
        Location clubLocation = new Location("");
        Location userLocation = new Location("");
        userLocation.setLatitude(latLng.latitude);
        userLocation.setLongitude(latLng.longitude);
        double distanceCal;

        if (governmentName.equals("Cairo") && gameName.equals("Swimming")) {
            allClubs = Constants.getSwimmingLocationsCairo();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= distance) {
                    clubs.add(allClubs.get(i));
                }
            }

            data.setValue(clubs);
        } else if (governmentName.equals("Cairo") && gameName.equals("Football")) {
            allClubs = Constants.getFootballLocationsCairo();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= distance) {
                    clubs.add(allClubs.get(i));
                }

                data.setValue(clubs);
            }
        } else if (governmentName.equals("Alex") && gameName.equals("Swimming")) {
            allClubs = Constants.getSwimmingLocationsAlex();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= distance) {
                    clubs.add(allClubs.get(i));
                }
            }
            data.setValue(clubs);
        } else if (governmentName.equals("Alex") && gameName.equals("Football")) {
            allClubs = Constants.getFootballLocationsAlex();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= distance) {
                    clubs.add(allClubs.get(i));
                }
            }
            data.setValue(clubs);
        }

        if (allClubs != null) {
            allClubs.clear();
        }
        return data;
    }
}
