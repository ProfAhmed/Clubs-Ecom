package com.ecom.clubs.data.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;

import com.ecom.clubs.constants.Constants;
import com.ecom.clubs.data.models.ClubModel;
import com.ecom.clubs.data.models.ClubRequestModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Repository {

    public LiveData<ArrayList<String>> getGovernments() {
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        ArrayList governments = new ArrayList();
        governments.add("Cairo");
        governments.add("Alex");
        data.setValue(governments);

        return data;
    }

    public LiveData<ArrayList<String>> getCities(String governmentName) {
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        if (governmentName.equals("Cairo")) {

            ArrayList<String> citiesCairo = new ArrayList<>();
            citiesCairo.add("Gisa");
            citiesCairo.add("6 October");
            data.setValue(citiesCairo);
        } else {
            ArrayList<String> citiesAlex = new ArrayList<>();
            citiesAlex.add("A");
            citiesAlex.add("B");
            data.setValue(citiesAlex);
        }

        return data;
    }

    public LiveData<ArrayList<String>> getGames() {
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        ArrayList<String> gameNames = new ArrayList<>();
        gameNames.add("Football");
        gameNames.add("Swimming");
        data.setValue(gameNames);
        return data;
    }

    public LiveData<ArrayList<ClubModel>> getClubs(ClubRequestModel requestModel) {
        MutableLiveData<ArrayList<ClubModel>> data = new MutableLiveData<>();
        ArrayList<ClubModel> allClubs = null;
        ArrayList<ClubModel> clubs = new ArrayList<>();
        Location clubLocation = new Location("");
        Location userLocation = new Location("");
        userLocation.setLatitude(requestModel.getLatLng().latitude);
        userLocation.setLongitude(requestModel.getLatLng().longitude);
        double distanceCal;

        if (requestModel.getGovernmentName().equals("Cairo") && requestModel.getGameName().equals("Swimming")) {
            allClubs = Constants.getSwimmingLocationsCairo();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= requestModel.getDistance()) {
                    clubs.add(allClubs.get(i));
                }
            }

            data.setValue(clubs);
        } else if (requestModel.getGovernmentName().equals("Cairo") && requestModel.getGameName().equals("Football")) {
            allClubs = Constants.getFootballLocationsCairo();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= requestModel.getDistance()) {
                    clubs.add(allClubs.get(i));
                }

                data.setValue(clubs);
            }
        } else if (requestModel.getGovernmentName().equals("Alex") && requestModel.getGameName().equals("Swimming")) {
            allClubs = Constants.getSwimmingLocationsAlex();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= requestModel.getDistance()) {
                    clubs.add(allClubs.get(i));
                }
            }
            data.setValue(clubs);
        } else if (requestModel.getGovernmentName().equals("Alex") && requestModel.getGameName().equals("Football")) {
            allClubs = Constants.getFootballLocationsAlex();
            for (int i = 0; i < allClubs.size(); i++) {
                clubLocation.setLatitude(allClubs.get(i).getLatLng().latitude);
                clubLocation.setLongitude(allClubs.get(i).getLatLng().longitude);
                distanceCal = userLocation.distanceTo(clubLocation) / 1000;
                if (distanceCal <= requestModel.getDistance()) {
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
