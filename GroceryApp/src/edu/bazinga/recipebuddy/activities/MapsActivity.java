package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.services.LocationsManager;
import edu.bazinga.recipebuddy.api.services.MapsManager;

public class MapsActivity extends Activity {
  
  protected LinearLayout layoutView;
  private MapsManager mapManager;
  private LocationsManager locationsManager;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.maps_layout);
    
    layoutView = (LinearLayout) findViewById(R.id.mapLayout);
    
    mapManager = MapsManager.requestInstance(this, savedInstanceState);
    /*
    locationsManager = LocationsManager.requestInstance(this, savedInstanceState);
    
    mapManager.requestAttach(layoutView);*/
  }
}