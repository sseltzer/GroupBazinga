package edu.bazinga.recipebuddy.data.collections;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.bazinga.recipebuddy.data.packets.FavoriteRecipe;
import edu.bazinga.recipebuddy.data.packets.GroceryList;

public class ApplicationData {
  
  private ArrayList<FavoriteRecipe> favorites;
  private ArrayList<GroceryList> groceryLists;
  
  //TODO write getters and setters
    
  public JSONObject toJSON() throws JSONException {
    //TODO let me know when everything else is done - Sean
    return null;
  }
  
  public void loadFromJSON(JSONObject jsonObject) throws JSONException {
    JSONArray jsonFavorites = jsonObject.getJSONArray("favorites");
    for (int i = 0; i < jsonFavorites.length(); ++i) {
      favorites.add(FavoriteRecipe.fromJSON(jsonFavorites.getJSONObject(i)));
    }
    JSONArray jsonGroceryLists = jsonObject.getJSONArray("groceryLists");
    for (int i = 0; i < jsonGroceryLists.length(); ++i) {
      groceryLists.add(GroceryList.fromJSON(jsonGroceryLists.getJSONObject(i)));
    }
  }
}
