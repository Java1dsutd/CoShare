package com.example.chris.coshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetDirections extends AppCompatActivity {

    MapView mMapView;
    private GoogleMap googleMap;
    private Marker marker;

    String s;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getdirections);

        s = getIntent().getStringExtra("LOCATION");


        ////////////
        //Map View//
        ////////////
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            //MapIntializer extend object
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                if (s.equals("Bugis Junction Tower")) {

                    googleMap = mMap;

                    // For showing a move to my location button
                    //googleMap.setMyLocationEnabled(true);

                    // For dropping a marker at a point on the Map
                    LatLng sg = new LatLng(1.299860, 103.855953);
                    marker = googleMap.addMarker(new MarkerOptions().position(sg).title("Bugis Junction Tower").snippet("230 Victoria Street 188024"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sg).zoom(18).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                else if (s.equals("Orchard Tower")){
                    googleMap = mMap;

                    // For showing a move to my location button
                    //googleMap.setMyLocationEnabled(true);

                    // For dropping a marker at a point on the Map
                    LatLng sg = new LatLng(1.306944, 103.829143);
                    marker = googleMap.addMarker(new MarkerOptions().position(sg).title("Orchard Tower").snippet("400 Orchard Road 238875"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sg).zoom(18).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            }
        });

        ////////////
        //Map View//
        ////////////


    }

    ////////////
    //Map View//
    ////////////
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    ////////////
    //Map View//
    ////////////
}
