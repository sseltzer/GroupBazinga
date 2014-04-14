/*Data Manager class
 * Data Manager is a class designed to read and write file data into a JSON data object
 * Authors: Matthew Tannehill
 *        : Sean Seltzer
 *        : Jamie Matos
 */
package edu.bazinga.recipebuddy.data.collections;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class DataManager {

  private static DataManager instance = null;         // Reference to data object
  private final String FILE_NAME = "RecipeBuddy.txt"; // Internal file name for proprietary data
  private ApplicationData appData = null;             // Data Structure used to segment internal data from JSON Object

  /**
   * Constructor used to load data from file
   * @param activity
   * @throws RecipeBuddyException 
   */
  private DataManager(Activity activity) throws RecipeBuddyException {   
    loadFile(activity);
  }
  
  /**
   * 
   * @param activity
   * @return current instance to data object, if none exist then it creates one
   * @throws RecipeBuddyException 
   */
  public static DataManager getInstance(Activity activity) throws RecipeBuddyException {
    if (instance == null) instance = new DataManager(activity);
    return instance;
  }
  
  public ApplicationData getAppData() {
    return appData;
  }

  /**
   * reads string data from a file and converts it into a JSON Object
   * @param activity
   * @throws RecipeBuddyException 
   */
  private void loadFile(Activity activity) throws RecipeBuddyException {
    Log.d("recipe", "loading file: " + FILE_NAME);
    if (activity == null) Log.d("recipe", "activity is null");
    else Log.d("recipe", "activity has data");
    String fileData = null;              // String read from file
    BufferedReader in = null;            // Input Stream
    InputStreamReader inputStreamReader = null;
    InputStream inputStream = null;
    

    File file = activity.getFileStreamPath(FILE_NAME);
    if (!file.exists()) {
      Log.d("recipe", "file does not exist: " + FILE_NAME);
      createNewFile(activity);
      appData = new ApplicationData();
      return;
    }
    
    // Try and open the file and read the stream from the file.
    try {
      inputStream = activity.openFileInput(FILE_NAME);
      if (inputStream == null) Log.d("recipe", "inputStream is null");
      else Log.d("recipe", "inputStream has data");
      if (inputStream == null) throw new RecipeBuddyException("Could not open the file.");  // Throw an error if we could not open the file.
      inputStreamReader = new InputStreamReader(inputStream);                               // Prepare stream for input.
      in = new BufferedReader(inputStreamReader);                                           // Add buffer to the input stream.
      fileData = in.readLine();                                                             // Read the first line from the file.
      Log.d("recipe", "file data: " + fileData);
      appData = ApplicationData.loadFromJSON(new JSONObject(fileData));                     // Load internal data from JSON Object.
    } catch (Exception e) {
      // If we encounter an exception, wrap the exception as our exception and rethrow it.
      e.printStackTrace();
      throw new RecipeBuddyException(e);
    } finally {
      // After we've thrown our exception, try and close the files one more time.
      try {
        in.close();
        inputStreamReader.close();
        inputStream.close();
      } catch (Exception e) {
        // If we failed to close the files, there's nothing we can do at this point. Take the
        // exception, wrap it, and rethrow it. 
        throw new RecipeBuddyException(e);
      }
    }
  }
  /**
   * takes a JSON data object and converts it to a string and then writes that string to a file
   * @param activity
   * @param jsonObject
   * @throws RecipeBuddyException 
   */
  public void writeFile(Activity activity) throws RecipeBuddyException {
    FileOutputStream     out      = null;                       //FOS
    BufferedOutputStream stream   = null;                       //BOS
    
    // Try and open the file and write the stream to the file.
    try {
      out = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE); //open file
      stream = new BufferedOutputStream(out);                    //create data stream
      stream.write(appData.toJSON().toString().getBytes());
    } catch (Exception e) {
      // If we encounter an exception, wrap the exception as our exception and rethrow it.
      throw new RecipeBuddyException(e);
    } finally {
      // After we've thrown our exception, try and close the files one more time.
      try {
        stream.close();
        out.close();
      } catch (Exception e) {
        // If we failed to close the files, there's nothing we can do at this point. Take the
        // exception, wrap it, and rethrow it. 
        throw new RecipeBuddyException(e);
      }
    }
  }
  
  private void createNewFile(Activity activity) throws RecipeBuddyException {
    FileOutputStream     out      = null;                       //FOS
    BufferedOutputStream stream   = null;                       //BOS
    
    // Try and open the file and write the stream to the file.
    try {
      out = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE); //open file
      stream = new BufferedOutputStream(out);                    //create data stream
      stream.write("".getBytes());
    } catch (Exception e) {
      // If we encounter an exception, wrap the exception as our exception and rethrow it.
      throw new RecipeBuddyException(e);
    } finally {
      // After we've thrown our exception, try and close the files one more time.
      try {
        stream.close();
        out.close();
      } catch (Exception e) {
        // If we failed to close the files, there's nothing we can do at this point. Take the
        // exception, wrap it, and rethrow it. 
        throw new RecipeBuddyException(e);
      }
    }
  }
  
  /*
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
  }*/
}
