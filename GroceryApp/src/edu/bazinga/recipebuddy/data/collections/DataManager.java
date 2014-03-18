package edu.bazinga.recipebuddy.data.collections;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;






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
    
    String fileData = null;   
    String file = "CEN4021";
    BufferedReader in = null;
    
    try {      
       
      in = new BufferedReader(new FileReader(file));      
      fileData = in.readLine();                        //Read the first line from the file      
      in.close();      
    } catch (IOException e) {      
      e.printStackTrace();
    }    
    
    JSONObject jsonObject;                             //
    try {
      jsonObject = new JSONObject(fileData);
      appData.loadFromJSON(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private InputStream openFileInput(String string) {
    // TODO Auto-generated method stub
    return null;
  }
  
 
}
