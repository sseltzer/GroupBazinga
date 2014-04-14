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
    // This object is a singleton service, this is a lazy init getInstance function.
    if (instance == null) instance = new LocationsManager(context, savedInstanceState);
    return instance;
  }
  public LatLng getLatLng() {
    // Attempt to retrieve the users location from the GPS provider.
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    // If the location could not be obtained, fall back on the network provider.
    if (location == null) {
      locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    // If the network location could not be obtained, default to the passive provider.
    if (location == null) {
      locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    }
    // If all else fails, and we have absolutely no way to obtain our location, default to a generic location.
    if (location == null) {
      return new LatLng(30.1586, -85.6603);
    }
    return new LatLng(location.getLatitude(), location.getLongitude());
  }
}
