package edu.bazinga.recipebuddy.data.collections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class DataManager {

  private static DataManager instance = null;
  String file = "CEN4021.txt";

  public static DataManager getInstance() {
    instance = null;
    // if (instance == null) instance = new DataManager();
    return instance;
  }

  private ApplicationData appData = null;

  public DataManager(Activity activity) {
    writeFile(activity, null);
    
    //loadFile();
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
    
    try {
      out = activity.openFileOutput(file, Context.MODE_PRIVATE);
      ObjectOutputStream oos = new ObjectOutputStream(out);
      oos.writeObject(jsonObjectTest); // object would go here
      oos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
  }

  private void loadFile() {

    String fileData = null;
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(file));
      fileData = in.readLine(); // Read the first line from the file
      Log.d("recipe", fileData);
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    JSONObject jsonObject;
    try {
      jsonObject = new JSONObject(fileData);
      appData.loadFromJSON(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

}
