package com.example.beerbudz;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE); // Here is where you set the map typ


        // Add a marker in Sydney and move the camera
        LatLng brewery1 = new LatLng(27.680955, -97.279415);
        mMap.addMarker(new MarkerOptions().position(brewery1).title("Lorelei Brewing Company").snippet("520 N A. S Drive"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brewery1));

        LatLng brewery2 = new LatLng(27.662095, -97.372111);
        mMap.addMarker(new MarkerOptions().position(brewery2).title("Lazy Beach Brewing Company").snippet("7522 Bichon Drive #100"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brewery2));

        LatLng brewery3 = new LatLng(27.802307, -97.395595);
        mMap.addMarker(new MarkerOptions().position(brewery3).title("Rebel Toad Brewing Company").snippet("425 William Street"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brewery3));

        LatLng brewery4 = new LatLng(27.809257, -97.395591);
        mMap.addMarker(new MarkerOptions().position(brewery4).title("Railroad Brewing Company").snippet("1214 N Chaparral Street"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brewery4));

        LatLng brewery5 = new LatLng(27.758849, -97.419649);
        mMap.addMarker(new MarkerOptions().position(brewery5).title("Psi Brewing Company").snippet("4135 Ayers Street"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brewery5));


    }

}


