/*Data Manager class
 * Data Manager is a class designed to read and write file data into a JSON data object
 * Authors: Matthew Tannehill
 *        : Sean Seltzer
 *        : Jamie Matos
 */
package edu.bazinga.recipebuddy.data.collections;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class DataManager {

  private static DataManager instance = null;     //reference to data object
  String file = "CEN4021.txt";                    //Internal file name for proprietary data
  private ApplicationData appData = null;         //Data Structure used to segment internal data from JSON Object

  //DataManger()
  //Constructor used to load data from file
  private DataManager(Activity activity) {   
    loadFile(activity);
  }
  
  //getInstance() 
  //returns current instance to data object, if none exist then it creates one
  public static DataManager getInstance(Activity activity) {
    if (instance == null) instance = new DataManager(activity);
    return instance;
  }    
  
  //testJsonObject()
  //****TEST**** remove when testing is done
  //creates dummy data to test file write and read
  private JSONObject testJsonObject()  {
    String fileData = " {\"ingredients\":[\"Hot Peppers\",\"Hot Peppers\",\"Hot Peppers\"],\"Rating\":\"Five Stars\",\"Recipe_ID\":\"101015\",\"Recipe_Name\":\"Onion Soup\"} ";
    JSONObject jsonObjectTest = null;
    try {
      jsonObjectTest = new JSONObject(fileData);
    } catch (JSONException e1) {
      e1.printStackTrace();    
    }
    return jsonObjectTest;
    
  }
  //writeFile()
  //takes a JSON data object and converts it to a string and then writes that string to a file
  private void writeFile(Activity activity, JSONObject jsonObject) {
   
    String               fileData = jsonObject.toString();      //convert JSON to string and save data
    FileOutputStream     out      = null;                       //FOS
    BufferedOutputStream stream   = null;                       //BOS
    
    try {
      out = activity.openFileOutput(file, Context.MODE_PRIVATE); //open file
      stream = new BufferedOutputStream(out);                    //create data stream
      stream.write(fileData.getBytes());                         //write data stream to file
      stream.close();                                            //close stream
      out.close();                                               //close file
    } catch (FileNotFoundException e) {
      e.printStackTrace();//error if file not found
    } catch (IOException e) {
      e.printStackTrace();//error on file write
    }
  }

  //loadFile()
  //reads string data from a file and converts it into a JSON Object
  private void loadFile(Activity activity) {

    String         fileData   = null;   //String read from file
    BufferedReader in         = null;   //Input Stream
    JSONObject     jsonObject = null;   //JSON

    try {
      InputStream inputStream = activity.openFileInput(file);                      //open file and input stream
      if ( inputStream != null) {//if successful         
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  //prepare stream for input
        in = new BufferedReader(inputStreamReader);                                //add buffer to the input stream
        fileData = in.readLine();                                                  //read the first line from the file      
        in.close();                                                                //close stream
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    try {
      jsonObject = new JSONObject(fileData);                    //create JSON Object from string
      appData.loadFromJSON(jsonObject);                         //Load internal data from JSON Object
    } catch (JSONException e) {//error if JSON fails
      e.printStackTrace();
    }
  }
}
