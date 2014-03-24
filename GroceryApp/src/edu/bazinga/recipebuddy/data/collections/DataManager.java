package edu.bazinga.recipebuddy.data.collections;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class DataManager {

  private static DataManager instance = null;
  String file = "CEN4021222.txt";

  public static DataManager getInstance() {
    instance = null;
    // if (instance == null) instance = new DataManager();
    return instance;
  }

  private ApplicationData appData = null;

  public DataManager(Activity activity) {
    writeFile(activity, null);
    
    loadFile(activity);
  }

  private void writeFile(Activity activity, JSONObject jsonObject) {
    // String appDataStr = appData.toJSONString();
    String fileData = " {\"ingredients\":[\"Hot Peppers\",\"Hot Peppers\",\"Hot Peppers\"],\"Rating\":\"Five Stars\",\"Recipe_ID\":\"101015\",\"Recipe_Name\":\"Onion Soup\"} ";
    JSONObject jsonObjectTest = null;
    FileOutputStream out;

    try {
      jsonObjectTest = new JSONObject(fileData);
    } catch (JSONException e1) {
      e1.printStackTrace();
    }
    BufferedOutputStream stream;
    try {
      out = activity.openFileOutput(file, Context.MODE_PRIVATE);
      stream = new BufferedOutputStream(out);
      stream.write(fileData.getBytes());
      stream.close();
      out.close();
   
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
  }

  private void loadFile(Activity activity) {

    String fileData = null;
    BufferedReader in = null;

    try {
      InputStream inputStream = activity.openFileInput(file);
      
      if ( inputStream != null) {
        
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      in = new BufferedReader(inputStreamReader);
      fileData = in.readLine(); // Read the first line from the file
      Log.d("recipe", fileData);
      in.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  /*

    JSONObject jsonObject;
    try {
      jsonObject = new JSONObject(fileData);
      appData.loadFromJSON(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }*/
  }

}
