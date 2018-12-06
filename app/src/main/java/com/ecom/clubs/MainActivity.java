package com.ecom.clubs;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telecom.RemoteConnection;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.clubs.data.models.ClubRequestModel;
import com.ecom.clubs.data.repositories.Repository;
import com.ecom.clubs.viewmodels.CitiesViewModel;
import com.ecom.clubs.viewmodels.ClubsViewModel;
import com.ecom.clubs.viewmodels.GamesViewModel;
import com.ecom.clubs.viewmodels.GovernmentViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapClickListener, LocationListener {

    @BindView(R.id.spGovernorate)
    Spinner spGovernment;

    @BindView(R.id.spCity)
    Spinner spCity;

    @BindView(R.id.spGame)
    Spinner spGame;

    @BindView(R.id.etDistance)
    EditText etDistance;

    @BindView(R.id.btnOk)
    Button btnOk;

    GovernmentViewModel governmentViewModel;
    CitiesViewModel citiesViewModel;
    GamesViewModel gamesViewModel;
    ClubsViewModel clubsViewModel;

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    public static final int REQUEST_LOCATION_CODE = 99;
    double curr_latitude, curr_longitude;
    private LocationManager locationManager;

    // declare lists for spinners
    private ArrayList<String> governmentsList;
    private ArrayList<String> citiesList;
    private ArrayList<String> gamesList;

    // declare Lists for Spinners
    private ArrayAdapter<String> dataAdapterGovernment;
    private ArrayAdapter<String> dataAdapterCity;
    private ArrayAdapter<String> dataAdapterGames;

    private String governmentInput;
    private String cityInput;
    private String gameInput;

    View mapView; // for change Location ImageButton on map


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        governmentViewModel = ViewModelProviders.of(this).get(GovernmentViewModel.class);
        citiesViewModel = ViewModelProviders.of(this).get(CitiesViewModel.class);
        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel.class);
        clubsViewModel = ViewModelProviders.of(this).get(ClubsViewModel.class);

        initLists();

        initAdapter();

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_CODE);
        }

        mMap.setOnMapClickListener(this);

        // change Location ImageButton on map
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            bulidGoogleApiClient();
                            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                mMap.setMyLocationEnabled(true);
                                LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
                            }
                        }

                    }

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }

    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please enable it or cancel to get location manually")
                .setCancelable(false)
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Please Click on your location on map ", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        curr_latitude = location.getLatitude();
        curr_longitude = location.getLongitude();
        Toast.makeText(this, "" + location.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Location is", location.toString());
        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            updateMap(point);
            marker(point);
        }
    }

    @Override
    public void onMapClick(LatLng point) {

        Toast.makeText(this, "Point is" + point.toString(), Toast.LENGTH_SHORT).show();
        marker(point);

    }

    void marker(LatLng point) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(point, 15);
        mMap.animateCamera(cameraUpdate);
    }

    private void updateMap(LatLng point) {

        governmentViewModel.getGovernments().observe(this, strings -> {

            for (int i = 0; i < strings.size(); i++) {
                governmentsList.add(strings.get(i));
            }
            dataAdapterGovernment.notifyDataSetChanged();
            Log.v("Government", strings.toString());
        });


        spGovernment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                governmentInput = parent.getItemAtPosition(position).toString();
                citiesViewModel.setGovernmentId(governmentInput);
                citiesViewModel.getCities().observe(MainActivity.this, strings -> {
                    if (citiesList.size() > 0) {
                        citiesList.clear();
                        citiesList.add("City");
                    }
                    for (int i = 0; i < strings.size(); i++) {
                        citiesList.add(strings.get(i));
                    }
                    dataAdapterCity.notifyDataSetChanged();

                    Log.v("Cities", strings.toString());
                });
                Toast.makeText(MainActivity.this, governmentInput, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityInput = parent.getItemAtPosition(position).toString();
                if (gamesList.size() > 0) {
                    gamesList.clear();
                    gamesList.add("Game");
                }
                gamesViewModel.getGames().observe(MainActivity.this, strings -> {

                    for (int i = 0; i < strings.size(); i++) {
                        gamesList.add(strings.get(i));
                    }
                    dataAdapterGames.notifyDataSetChanged();
                    Log.v("Games", strings.toString());
                });
                Toast.makeText(MainActivity.this, cityInput, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameInput = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, gameInput, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDistance.getText().length() > 0 && governmentsList.size() > 1 && citiesList.size() > 1 && gamesList.size() > 1) {
                    double distance = Double.parseDouble(etDistance.getText().toString());
                    ClubRequestModel request = new ClubRequestModel(governmentInput, cityInput, gameInput, point, distance);
                    clubsViewModel.setClubRequest(request);
                    clubsViewModel.getClubs().observe(MainActivity.this, clubs -> {

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        for (int i = 0; i < clubs.size(); i++) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(clubs.get(i).getLatLng());
                            markerOptions.title(clubs.get(i).getName());
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions);
                            builder.include(markerOptions.getPosition());
                        }
                        LatLngBounds bounds = builder.build();
                        int padding = 5; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,  padding);
                        mMap.animateCamera(cu);

                        Toast.makeText(MainActivity.this, clubs.toString(), Toast.LENGTH_SHORT).show();
                        Log.v("Clubs", clubs.toString());
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter All Data ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initLists() {
        governmentsList = new ArrayList<>();
        citiesList = new ArrayList<>();
        gamesList = new ArrayList<>();
    }

    private void initAdapter() {
        String governmentDefault = getResources().getString(R.string.government);
        String cityDefault = getResources().getString(R.string.city);
        String gameDefault = getResources().getString(R.string.code);

        governmentsList.add(governmentDefault);
        citiesList.add(cityDefault);
        gamesList.add(gameDefault);

        // Creating adapter for spinner
        dataAdapterGovernment = new ArrayAdapter<>(this, R.layout.spinner_item, governmentsList);
        dataAdapterCity = new ArrayAdapter<>(this, R.layout.spinner_item, citiesList);
        dataAdapterGames = new ArrayAdapter<>(this, R.layout.spinner_item, gamesList);

        // Drop down layout style - list view with radio button
        dataAdapterGovernment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterGames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spGovernment.setAdapter(dataAdapterGovernment);
        spCity.setAdapter(dataAdapterCity);
        spGame.setAdapter(dataAdapterGames);
    }

}
