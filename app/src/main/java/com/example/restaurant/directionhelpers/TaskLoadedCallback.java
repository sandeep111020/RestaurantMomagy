package com.example.restaurant.directionhelpers;

import android.location.Location;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);

    void onLocationChanged(Location location);
}