package edu.bazinga.recipebuddy.api.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.retrievers.JSONRetriever;
import edu.bazinga.recipebuddy.data.packets.Place;


public class PlacesManager {
  
  private static PlacesManager instance = null;
  
  private final String PLACESKEY;
  private EnumMap<QueryType, ArrayList<Place>> queryMap;
  
  private PlacesManager(Context context, Bundle savedInstanceState) {
    PLACESKEY = context.getString(R.string.placeskey);
    queryMap = new EnumMap<QueryType, ArrayList<Place>>(QueryType.class);
  }
  
  public ArrayList<Place> query(QueryType queryType, LatLng location) throws InterruptedException, ExecutionException {
 // Perform a query with a default of 2000 meters.
    return query(queryType, location, 2000);
  }
  public ArrayList<Place> query(QueryType queryType, LatLng location, int distance) throws InterruptedException, ExecutionException {
    // Perform a query with the given distance. 
    JSONRetriever placesRetriever = new JSONRetriever();
    ArrayList<Place> queryList = null;
    try {
      //Log.d("recipe", "Query: "+ buildQuery(queryType, location, distance));
      AsyncTask<String, Void, String> task = placesRetriever.execute(buildQuery(queryType, location, distance));
      String retStr = task.get();
      queryList = parseResonse(retStr);
      queryMap.put(queryType, queryList);
    } catch (UnsupportedEncodingException e) {
      // Ignore this one. This should never go wrong. Stack trace if it does.
      e.printStackTrace();
    }

    return queryList;
  }
  
  public int stringMilesToMeters(String miles) {
    // Convert miles to meters for use in the query.
    miles = miles.substring(0, miles.indexOf(" "));
    return (int) (Integer.parseInt(miles) * 1609.34);
  }
  
  public static PlacesManager requestInstance(Context context, Bundle savedInstanceState) {
    // This object is a singleton service, this is a lazy init getInstance function.
    if (instance == null) instance = new PlacesManager(context, savedInstanceState);
    return instance;
  }
  // UTF-8 Fix from here: http://stackoverflow.com/questions/13153625/android-google-maps-search-by-keywords
  private String buildQuery(QueryType queryType, LatLng location, int distance) throws UnsupportedEncodingException {
    // Build a simple query string to send to Google Places.
    return "https://maps.googleapis.com/maps/api/place/search/json?" + 
        "location=" + location.latitude + "," + location.longitude +
        "&radius=" + distance +
        "&types=" + URLEncoder.encode(queryType.getQueryStr(), "UTF-8") +
        "&sensor=true" +
        "&key=" + PLACESKEY;
  }
  
  private ArrayList<Place> parseResonse(String json) {
    // Parse the JSON return response. This JSON format is specific to the response from Google Places.
    ArrayList<Place> placeList = new ArrayList<Place>();
    try {
      JSONObject entries = new JSONObject(json);
      JSONArray results = entries.getJSONArray("results");
      for (int i = 0; i < results.length(); ++i) {
        JSONObject name = results.getJSONObject(i);
        JSONObject geom = (JSONObject) name.get("geometry");
        JSONObject loc = (JSONObject) geom.get("location");
        
        String nameStr = name.get("name").toString();
        String addressStr = name.get("vicinity").toString();
        
        double lat = Double.parseDouble(loc.get("lat").toString());
        double lng = Double.parseDouble(loc.get("lng").toString());
        
        Place place = new Place(nameStr, addressStr, new LatLng(lat, lng));
        placeList.add(place);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return placeList;
  }
  
  public enum QueryType {
    // A few selections of search queries. Only GROCERY is used at the moment.
    FOOD("food|restaurant|cafe"),
    GROCERY("grocery_or_supermarket"),
    SHOPPING("store|book_store|clothing_store|convenience_store|electronics_store|department_store|shopping_mall"),
    SCHOOLS("school"),
    MUSEUMS("museums");
    private String queryStr;
    private QueryType(String queryStr) {
      this.queryStr = queryStr;
    }
    public String getQueryStr() {
      return queryStr;
    }
  }
}
