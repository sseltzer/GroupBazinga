package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.services.MapsManager;

public class MapsActivity extends Activity {
  
  protected LinearLayout layoutView;
  private MapsManager mapManager;
  //private LocationsManager locationsManager;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.maps_layout);
    
    layoutView = (LinearLayout) findViewById(R.id.mapLayout);
    mapManager = MapsManager.requestInstance(this, savedInstanceState);
    mapManager.onCreate(this, savedInstanceState);
    mapManager.requestAttach(layoutView);
    //locationsManager = LocationsManager.requestInstance(this, savedInstanceState);
    //Log.d("asdf", locationsManager.getLatLng().toString());
  }
  
  @Override
  protected void onResume() {
    mapManager.onResume();
    super.onResume();
  }
  @Override
  protected void onPause() {
    mapManager.onPause();
    super.onPause();
  }
  @Override
  protected void onDestroy() {
    mapManager.onDestroy();
    super.onDestroy();
  }
  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapManager.onLowMemory();
  }
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapManager.onSaveInstanceState(outState);
  }
}