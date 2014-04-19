package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.services.MapsManager;

public class MapsActivity extends Activity {
  
 protected LinearLayout layoutView;
  private MapsManager mapManager;
  //private LocationsManager locationsManager;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Grab our content xml (this just contains a linear layout) to get our anchor point for the map.
    setContentView(R.layout.maps_layout);
    
    layoutView = (LinearLayout) findViewById(R.id.mapLayout);
    mapManager = MapsManager.requestInstance(this, savedInstanceState);
    mapManager.onCreate(this, savedInstanceState);
    mapManager.requestAttach(layoutView);
    //Log.d("recipe", locationsManager.getLatLng().toString());
  }
  /*@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
  {

	  // Creates option menu
	  setHasOptionsMenu(true);	// The onCreateOptionsMenu must not return a boolean in order to work
      View layoutView = inflater.inflate(R.layout.maps_layout, container, false);
      layoutView = (LinearLayout) layoutView.findViewById(R.id.mapLayout);
      mapManager = MapsManager.requestInstance(this, savedInstanceState);
      mapManager.getActivity().onCreate(this, savedInstanceState);
      mapManager.requestAttach(layoutView);

      return layoutView;
  }
   */
  //@Override
  public void onResume() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapManager.onResume();
    //super.onResume();
  }
  //@Override
  public void onPause() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapManager.onPause();
   // super.onPause();
  }
  //@Override
  public void onDestroy() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapManager.onDestroy();
   // super.onDestroy();
  }
  //@Override
  public void onLowMemory() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    //super.onLowMemory();
    mapManager.onLowMemory();
  }
  ///@Override
  public void onSaveInstanceState(Bundle outState) {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
   // super.onSaveInstanceState(outState);
    mapManager.onSaveInstanceState(outState);
  }
}