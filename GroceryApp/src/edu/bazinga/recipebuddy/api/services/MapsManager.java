package edu.bazinga.recipebuddy.api.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.services.PlacesManager.QueryType;
import edu.bazinga.recipebuddy.data.packets.Place;
import edu.bazinga.recipebuddy.views.PlaceInfo;


public class MapsManager {
  
  private static MapsManager instance = null;
  
  private LinearLayout currentAnchor;
  private LinearLayout layout;
  private MapView mapView;
  private Spinner layerSpinner;
  private Spinner distanceSpinner;
  private Button searchButton;
  private QueryType lastQuery = QueryType.GROCERY;
  private PlacesManager mPm;
  private LocationsManager mLm;
  
  
  private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
    // This is the selected item listener for the distance dropdown - once a distance is selected,
    // re-search Google Places with the selected distance.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      if (parent.equals(layerSpinner)) {
        setLayer(MapMode.getMapModeFromString((String) parent.getItemAtPosition(position)));
        return;
      }
      if (parent.equals(distanceSpinner)) doSearch();
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
  };
  private OnClickListener onClickListener = new OnClickListener() {
    // Listener for when the update button is pressed, re-search.
    @Override
    public void onClick(View v) {
      doSearch();
    }
  };
  
  private MapsManager(Context context, Bundle savedInstanceState) {
    // This is the private constructor for the singleton maps service.
    // Obtain an instance of the places and locations managers.
    mPm = PlacesManager.requestInstance(context, savedInstanceState);
    mLm = LocationsManager.requestInstance(context, savedInstanceState);
    // Initialize the Google Maps object from the Google Play Services Lib.
    try {
      MapsInitializer.initialize(context);
    } catch (GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
    // Initialize String constants from the abstracted Strings file in the res directory.
    MapMode.NORMAL.setModeStr(context.getString(R.string.normal));
    MapMode.HYBRID.setModeStr(context.getString(R.string.hybrid));
    MapMode.SATELLITE.setModeStr(context.getString(R.string.satellite));
    // Create our Maps view using the Google Play Services Lib.
    generateView(context, savedInstanceState);
  }
  public static MapsManager requestInstance(Context context, Bundle savedInstanceState) {
    // This object is a singleton service, this is a lazy init getInstance function.
    if (instance == null) instance = new MapsManager(context, savedInstanceState);
    return instance;
  }
  
  private void generateView(Context context, Bundle savedInstanceState) {
    // Using our anchor point (the layout object), create our view and attach our spinners and update button.
    layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    LinearLayout linearLayout = new LinearLayout(context);
    
    layerSpinner = new Spinner(context);
    //Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.layers_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    layerSpinner.setAdapter(adapter);
    layerSpinner.setOnItemSelectedListener(onItemSelectedListener);

    ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(context, R.array.distance_array, android.R.layout.simple_spinner_item);
    distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    distanceSpinner = new Spinner(context);
    distanceSpinner.setAdapter(distanceAdapter);
    distanceSpinner.setOnItemSelectedListener(onItemSelectedListener);
    
    searchButton = new Button(context);
    searchButton.setText(R.string.searchString);
    searchButton.setOnClickListener(onClickListener);
    
    linearLayout.addView(distanceSpinner, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    linearLayout.addView(layerSpinner, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    linearLayout.addView(searchButton, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    
    layout.addView(linearLayout);
  }
  
  public void requestAttach(LinearLayout anchor) {
    // Attach the view to the layout.
    if (currentAnchor != null || anchor == null) return;
    currentAnchor = anchor;
    anchor.addView(layout);
    animateNewLatLng(mLm.getLatLng());
  }
  public void requestDetach(LinearLayout anchor) {
    // Detach the view from the layout.
    if (currentAnchor == null || !currentAnchor.equals(anchor)) return;
    currentAnchor.removeView(layout);
    currentAnchor = null;
    mapView.getMap().clear();
  }
  
  private void setLayer(MapMode mapMode) {
    // Set our map mode satellite, hybrid with street maps, or normal maps.
    if (mapView == null) return;
    GoogleMap map = mapView.getMap();
    if (map == null) return;
    switch(mapMode) {
      case NORMAL: map.setMapType(MAP_TYPE_NORMAL); break;
      case HYBRID: map.setMapType(MAP_TYPE_HYBRID); break;
      case SATELLITE: map.setMapType(MAP_TYPE_SATELLITE); break;
    }
  }
  public void animateNewLatLng(LatLng latLng) {
    // Animate our view to the given location (used when the GPS button is pressed, or latlng changes).
    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(11).build();
    mapView.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }
  
  public void markUp(QueryType queryType, ArrayList<Place> places) {
    // Add the pins to the map from the Google Places Query.
    if (places == null) return;
    lastQuery = queryType;
    //this.places = places;
    for(Place place : places) {
      //Log.d("recipe", "Place: " + place.getNameStr() + " Address: " + place.getAddressStr() + " LatLng: " + place.getLatLng());
      MarkerOptions options = new MarkerOptions();
      options.title(place.getNameStr());
      options.snippet(place.getAddressStr());
      options.position(place.getLatLng());
      mapView.getMap().addMarker(options);
    }
  }
  public void doSearch() {
    // Clear the map and perform another search using Google Places and map up the pins on the map.
    int distance = mPm.stringMilesToMeters((String) distanceSpinner.getSelectedItem());
    mapView.getMap().clear();
    try {
      markUp(lastQuery, mPm.query(lastQuery, mLm.getLatLng(), distance));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
  public void onCreate(Context context, Bundle savedInstanceState) {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    try {
      MapsInitializer.initialize(context);
    } catch (GooglePlayServicesNotAvailableException e) {
      e.printStackTrace();
    }
    mapView = new MapView(context);
    mapView.onCreate(savedInstanceState);
    mapView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    layout.addView(mapView);
    if (mapView.getMap() == null) return;
    mapView.getMap().setMyLocationEnabled(true);
    mapView.getMap().setInfoWindowAdapter(new PlaceInfo(context));
    setLayer(MapMode.getMapModeFromString((String) layerSpinner.getSelectedItem()));
  }
  public void onResume() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapView.onResume();
  }

  public void onPause() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapView.onPause();
  }

  public void onDestroy() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    requestDetach(currentAnchor);
    mapView.onDestroy();
    layout.removeView(mapView);
    mapView = null;
  }

  public void onLowMemory() {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapView.onLowMemory();
  }
  public void onSaveInstanceState(Bundle outState) {
    // Forward the application lifecycle functions from the activity to update the Google Play Services Lib objects.
    // THIS IS REQURED FOR THE MAP TO FUNCTION.
    mapView.onSaveInstanceState(outState);
  }
  
  public enum MapMode {
    // Map our string constants for use with the Google Play Services Lib.
    NORMAL,
    HYBRID,
    SATELLITE;
    private String modeStr = null;
    public void setModeStr(String modeStr) {
      this.modeStr = modeStr;
    }
    public String getModeStr() {
      return modeStr;
    }
    public static MapMode getMapModeFromString(String modeStr) {
      for(MapMode mode : MapMode.values()) if (mode.getModeStr().equals(modeStr)) return mode;
      return NORMAL;
    }
  }
}
