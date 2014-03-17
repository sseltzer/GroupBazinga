package edu.bazinga.recipebuddy.data.collections;

import org.json.JSONException;
import org.json.JSONObject;

public class DataManager {
  
  private static DataManager instance = null;
  
  public static DataManager getInstance() {
    if (instance == null) instance = new DataManager();
    return instance;
  }
  
  private ApplicationData appData = null;
  
  private DataManager() {
    loadFile();
  }
  
  private void writeFile() {
    //String appDataStr = appData.toJSONString();
    //TODO let me know when everything else is done - Sean
    //write file here
  }
  
  private void loadFile() {
    //TODO Load file here
    String fileData = null;
    
    
    
    JSONObject jsonObject;
    try {
      jsonObject = new JSONObject(fileData);
      appData.loadFromJSON(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
}
