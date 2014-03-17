package edu.bazinga.recipebuddy.data.packets;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class FavoriteRecipe {

  private String id = null;
  private String name = null;
  private String imageURL = null;
  private Bitmap image = null;
  
  //TODO write getters and setters
  
  public JSONObject toJSON() throws JSONException {
    //TODO
    return null;
  }
  public static FavoriteRecipe fromJSON(JSONObject jsonObject) {
    FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
    
    //TODO pull data out
    
    return favoriteRecipe;
  }
}
