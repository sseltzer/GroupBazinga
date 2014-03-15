package edu.bazinga.recipebuddy.api.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

public class LocationsManager {
  
  private static LocationsManager instance = null;
  
  private LocationManager locationManager;
  
  private LocationsManager(Context context, Bundle savedInstanceState) {
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }
  
  public static LocationsManager requestInstance(Context context, Bundle savedInstanceState) {
    if (instance == null) instance = new LocationsManager(context, savedInstanceState);
    return instance;
  }
  public LatLng getLatLng() {
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location == null) {
      locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    if (location == null) {
      locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    }
    if (location == null) {
      return new LatLng(30.1586, -85.6603);
    }
    return new LatLng(location.getLatitude(), location.getLongitude());
  }
}
