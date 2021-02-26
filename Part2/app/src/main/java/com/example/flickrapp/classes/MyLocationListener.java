package com.example.flickrapp.classes;

import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.flickrapp.R;

public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}
}