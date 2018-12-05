package com.ecom.clubs.constants;

import com.ecom.clubs.data.models.ClubModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Constants {
    public static String[] governments = {"Cairo", "Alex"};
    public static String[] citiesCairo = {"Gisa", "6 October"};
    public static String[] citiesAlex = {"a", "b"};
    public static String[] gameName = {"Football", "Swimming"};

    static ArrayList<ClubModel> swimmingLocationsCairo = new ArrayList<>();
    static ArrayList<ClubModel> footballLocationsCairo = new ArrayList<>();
    static ArrayList<ClubModel> swimmingLocationsAlex = new ArrayList<>();
    static ArrayList<ClubModel> footballLocationsAlex = new ArrayList<>();

    public static ArrayList getSwimmingLocationsCairo() {
        swimmingLocationsCairo.add(new ClubModel("Ahly", new LatLng(29, 30)));
        swimmingLocationsCairo.add(new ClubModel("Lll", new LatLng(30, 31)));
        swimmingLocationsCairo.add(new ClubModel("MMM", new LatLng(29, 32)));
        swimmingLocationsCairo.add(new ClubModel("DDD", new LatLng(29.5, 30.3)));
        swimmingLocationsCairo.add(new ClubModel("EEE", new LatLng(29.8, 30.9)));
        swimmingLocationsCairo.add(new ClubModel("PPP", new LatLng(28, 30)));

        return swimmingLocationsCairo;
    }

    public static ArrayList getFootballLocationsCairo() {
        footballLocationsCairo.add(new ClubModel("Ahly", new LatLng(29, 30)));
        footballLocationsCairo.add(new ClubModel("Lll", new LatLng(30, 31)));
        footballLocationsCairo.add(new ClubModel("MMM", new LatLng(30.2, 31.9)));
        footballLocationsCairo.add(new ClubModel("DDD", new LatLng(29.5, 30.3)));
        footballLocationsCairo.add(new ClubModel("EEE", new LatLng(29.8, 30.9)));
        footballLocationsCairo.add(new ClubModel("PPP", new LatLng(28, 30)));

        return footballLocationsCairo;
    }

    public static ArrayList getSwimmingLocationsAlex() {
        swimmingLocationsAlex.add(new ClubModel("Ahly", new LatLng(29, 30)));
        swimmingLocationsAlex.add(new ClubModel("Lll", new LatLng(30, 31)));
        swimmingLocationsAlex.add(new ClubModel("MMM", new LatLng(29, 32)));
        swimmingLocationsAlex.add(new ClubModel("DDD", new LatLng(29.5, 30.3)));
        swimmingLocationsAlex.add(new ClubModel("EEE", new LatLng(29.8, 30.9)));
        swimmingLocationsAlex.add(new ClubModel("PPP", new LatLng(28, 30)));

        return swimmingLocationsAlex;
    }

    public static ArrayList getFootballLocationsAlex() {
        footballLocationsAlex.add(new ClubModel("Ahly", new LatLng(29, 30)));
        footballLocationsAlex.add(new ClubModel("Lll", new LatLng(30, 31)));
        footballLocationsAlex.add(new ClubModel("MMM", new LatLng(29, 32)));
        footballLocationsAlex.add(new ClubModel("DDD", new LatLng(29.5, 30.3)));
        footballLocationsAlex.add(new ClubModel("EEE", new LatLng(29.8, 30.9)));
        footballLocationsAlex.add(new ClubModel("PPP", new LatLng(28, 30)));

        return footballLocationsAlex;
    }

}
