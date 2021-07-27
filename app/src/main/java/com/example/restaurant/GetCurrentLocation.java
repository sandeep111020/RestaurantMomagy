package com.example.restaurant;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;



import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class GetCurrentLocation implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = "GetCurrentLocation";
    public GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;
    public Location mLastLocation;
    public String latitude, longitude;
    private final int REQUEST_CHECK_SETTINGS = 300;
    Activity context;

    public GetCurrentLocation(Activity activity) {
        context = activity;
        buildGoogleApiClient();
        createLocationRequest();
    }

    public void connectGoogleApi() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disConnectGoogleApi() {

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // for enable device location
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates LS_state = locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(context, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        getCurrentLocation();

    }

    public void getCurrentLocation() {


        if (checkPermission()) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation == null) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            } else {

                latitude = String.format("%f", mLastLocation.getLatitude());
                longitude = String.format("%f", mLastLocation.getLongitude());

                if (!TextUtils.isEmpty(latitude) || !TextUtils.isEmpty(longitude)) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                }

            }


        } else {
            requestPermission();
        }


    }


    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(context, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 100);

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {

        if (mGoogleApiClient != null) {

            if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
            } else if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }

        }

    }

}