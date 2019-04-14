package com.example.locate_photo_app.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

public class GPSLocation extends Service implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false, isNetworkEnabled = false, canGetLocation = false;

    Location location;
    double latitude, longitude;

    String city, country;
    Geocoder geocoder;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 100 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GPSLocation(Context context) {
        this.mContext = context;
        geocoder = new Geocoder(mContext);
        getLocation();
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);

            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
                this.canGetLocation = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            setAddress(location);
                        }
                    }
                }

                if (isGPSEnabled) {
                    if (location == null) {

                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                setAddress(location);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return  country;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void setAddress(Location lc) throws IOException {
        latitude = lc.getLatitude();
        longitude = lc.getLongitude();

        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);

        city = addresses.get(0).getLocality();
        country = addresses.get(0).getCountryName();
    }

    public String getAddress() {
        String address = getCity() + ", " + getCountry();

        return  address;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}