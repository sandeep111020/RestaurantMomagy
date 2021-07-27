package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.Models.Delieverylatlong;
import com.example.restaurant.Models.Mylatlong;
import com.example.restaurant.directionhelpers.FetchURL;
import com.example.restaurant.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
    GoogleMap mMap;
    private MarkerOptions place1, place2;
    EditText getDirection, getloc;
    private Polyline currentPolyline;
    String userlat;
    String userlong;
    String dellat, delong;
    String id;

    LatLng point;
    private DatabaseReference db;
    private GoogleMap googleMap;
    MapFragment mapFragment;

    String Slatitude, Slongitude;

    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private String mprovider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getDirection = findViewById(R.id.btnGetDirection);
        getloc = findViewById(R.id.btnGetDirection2);
        id = getIntent().getStringExtra("id");

        Slatitude = "0.0";
        Slongitude = "0.0";
        userlat = getIntent().getStringExtra("lat");
        userlong = getIntent().getStringExtra("lon");


        // Toast.makeText(MapActivity.this,id,Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(MapActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },100);





        }








        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });
        getLocation();
        //27.658143,85.3199503
        //27.667491,85.3208583
        //place2 = new MarkerOptions().position(new LatLng(17.891755, 83.455229)).title("Order Location");
       //
        point = new LatLng(Double.parseDouble(userlat), Double.parseDouble(userlong));


        place2=new MarkerOptions().position(point).title("Order Location");

        // Drawing the marker at the coordinates
        //place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(getDirection.getText().toString()), Double.parseDouble(getloc.getText().toString()))).title("Order Location");
        place1 = new MarkerOptions().position(new LatLng(17.805215731092858, 83.37862986786706)).title("Delivery Boy");

        mapFragment= (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
        new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
       // fetchLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MapActivity.this);

            mprovider = locationManager.getBestProvider(new Criteria(), false);
            Location location = locationManager.getLastKnownLocation(mprovider);
            onLocationChanged(location);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    @Override
    public void onLocationChanged(Location location) {

        // locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        Toast.makeText(MapActivity.this,"Current Location: " + location.getLatitude() + ", " + location.getLongitude() , Toast.LENGTH_SHORT).show();
        Slatitude=String.valueOf(location.getLatitude());
        Slongitude=String.valueOf(location.getLongitude());
        getloc.setText(Slatitude+Slongitude+"@@");
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("Tracking").child(id);
        Delieverylatlong data=new Delieverylatlong(String.valueOf(Slatitude),String.valueOf(Slongitude));
        db.child("DelBoy").setValue(data);
        onMapReady(mMap);
    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MapActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        // mMap.addMarker(place1);
        mMap.addMarker(place2);
        //Slatitude="65.75335";
        //Slongitude="-86.8644";
        getloc.setText(Slatitude+Slongitude+"");
        LatLng latLng = new LatLng(Double.parseDouble(Slatitude), Double.parseDouble(Slongitude));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Delivery Boy");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
    }

  /*  private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    mapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }*/


}