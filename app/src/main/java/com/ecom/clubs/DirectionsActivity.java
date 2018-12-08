package com.ecom.clubs;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.ecom.clubs.data.models.ClubModel;
import com.ecom.clubs.events.EventClub;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class DirectionsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ClubModel clubModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Toast.makeText(this, "OnCreate" + clubModel.toString().toString(),
                Toast.LENGTH_SHORT).show();

        GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_key))
                .from(new LatLng(37.7681994, -122.444538))
                .to(new LatLng(37.7749003, -122.4034934))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Toast.makeText(DirectionsActivity.this, "Succsess Directions", Toast.LENGTH_SHORT).show();
                            Leg leg = direction.getRouteList().get(0).getLegList().get(0);
                            ArrayList<LatLng> directionsPositionList = leg.getDirectionPoint();
                            PolylineOptions polyline = DirectionConverter.createPolyline(DirectionsActivity.this, directionsPositionList, 5, Color.RED);
                            mMap.addPolyline(polyline);
                        } else {
                            // Do something
                            Toast.makeText(DirectionsActivity.this, "What is worng !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                        Toast.makeText(DirectionsActivity.this, "Failer Direction", Toast.LENGTH_SHORT).show();
                        Log.v("Directions Failer", t.toString());
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventClub event) {

        Toast.makeText(this, event.getClubModel().toString(),
                Toast.LENGTH_SHORT).show();
        clubModel = event.getClubModel();
        EventBus.getDefault().removeStickyEvent(event.toString()); // don't forget to remove the sticky event if youre done with it
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
