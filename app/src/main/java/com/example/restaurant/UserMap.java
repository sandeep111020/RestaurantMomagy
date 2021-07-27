package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserMap extends AppCompatActivity implements OnMapReadyCallback {
    String mylat,mylon,dlat,dlon;
    MapFragment mapFragment;
    private MarkerOptions place1,place2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        mylat=getIntent().getStringExtra("lat");
        mylon=getIntent().getStringExtra("lon");
        dlat=getIntent().getStringExtra("dlat");
        dlon=getIntent().getStringExtra("dlon");

        place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(dlat), Double.parseDouble(dlon))).title("Delivery Boy");

        place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(mylat), Double.parseDouble(mylon))).title("Order Location");

        mapFragment= (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(place1);
        googleMap.addMarker(place2);
        LatLng latLng = new LatLng(Double.parseDouble(mylat), Double.parseDouble(mylon));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
    }
}