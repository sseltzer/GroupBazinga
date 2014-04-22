package edu.bazinga.recipebuddy.data.collections;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.data.packets.Recipe;

public class ApplicationData {
  
  private ArrayList<Recipe> favorites;
  private ArrayList<GroceryList> groceryLists;
  private ArrayList<Recipe> queries;
  
  public ApplicationData() {
    this(new ArrayList<Recipe>(), new ArrayList<GroceryList>());
  }
  public ApplicationData(ArrayList<Recipe> favorites, ArrayList<GroceryList> groceryLists) {
    this.favorites = favorites;
    this.groceryLists = groceryLists;
    this.queries = new ArrayList<Recipe>();
  }
  
  
  public void addFavorite(Recipe recipe) {
    favorites.add(recipe);
  }
  public void addFavorites(ArrayList<Recipe> favorites) {
    this.favorites.addAll(favorites);
  }
  public void removeFavorites(int i) {
    favorites.remove(i);
  }
  public ArrayList<Recipe> getFavorites() {
    return favorites;
  }
  
  public void addGroceryList(GroceryList groceryList) {
    groceryLists.add(groceryList);
  }
  public void addGroceryList(ArrayList<GroceryList> groceryList) {
    this.groceryLists.addAll(groceryList);
  }
  public void removeGroceryList(int i) {
    groceryLists.remove(i);
  }
  public ArrayList<GroceryList> getGroceryList() {
    return groceryLists;
  }
  
  public void setNewQueries(ArrayList<Recipe> queries) {
    this.queries = queries;
  }
  public void addQueries(ArrayList<Recipe> queries) {
    queries.addAll(queries);
  }
  public ArrayList<Recipe> getQueries() {
    return queries;
  }
    
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    
    JSONArray favoritesArray = new JSONArray();
    for (int i = 0; i < favorites.size(); ++i) favoritesArray.put(favorites.get(i).toJSON());
    
    JSONArray groceryListsArray = new JSONArray();
    for (int i = 0; i < groceryLists.size(); ++i) groceryListsArray.put(groceryLists.get(i).toJSON());

    ret.put("version", DataManager.FILE_VERSION);
    
    ret.put("favorites", favoritesArray);
    ret.put("groceryLists", groceryListsArray);
    
    return ret;
  }
  
  public static ApplicationData loadFromJSON(JSONObject jsonObject) throws JSONException {
    ArrayList<Recipe> favorites = new ArrayList<Recipe>();
    ArrayList<GroceryList> groceryLists = new ArrayList<GroceryList>();
    
    JSONArray jsonFavorites = jsonObject.getJSONArray("favorites");
    for (int i = 0; i < jsonFavorites.length(); ++i) {
      favorites.add(Recipe.fromJSON(jsonFavorites.getJSONObject(i)));
    }
    JSONArray jsonGroceryLists = jsonObject.getJSONArray("groceryLists");
    for (int i = 0; i < jsonGroceryLists.length(); ++i) {
      groceryLists.add(GroceryList.fromJSON(jsonGroceryLists.getJSONObject(i)));
    }
    
    return new ApplicationData(favorites, groceryLists);
  }
}
