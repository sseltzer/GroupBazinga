package edu.bazinga.recipebuddy.data.packets;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroceryList {
  
  private String listName = null;
  private ArrayList<GroceryItem> groceryItems = null;
  
  public GroceryList(String listName) {
    this.listName = listName;
    groceryItems = new ArrayList<GroceryItem>();
  }
  public GroceryList(String listName, ArrayList<GroceryItem> groceryItems) {
    this.listName = listName;
    this.groceryItems = new ArrayList<GroceryItem>(groceryItems);
  }
  
  public String getListName() {
    return listName;
  }
  public void setListName(String listName) {
    this.listName = listName;
  }
  public void addGroceryItem(GroceryItem groceryItem) {
    groceryItems.add(groceryItem);
  }
  public void addGroceryItems(ArrayList<GroceryItem> groceryItems) {
    this.groceryItems.addAll(groceryItems);
  }
  public void removeGroceryItem(int i) {
    groceryItems.remove(i);
  }
  
  public JSONObject toJSON() throws JSONException {
    //TODO
    // JSON example 
    //  {"ingredients":["Hot Peppers","Hot Peppers","Hot Peppers"],"Rating":"Five Stars","Recipe_ID":"101015","Recipe_Name":"Onion Soup"} 
    return null;
  }
  
  public static GroceryList fromJSON(JSONObject jsonObject) throws JSONException {
    String listName = jsonObject.getString("listName");                           // Get the name out of the JSON object and assign our new list object it's name.
    
    ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();           // Create a new collection of our items.
    JSONArray jsonGroceryItems = jsonObject.getJSONArray("groceryItems");         // Get our array of json grocery items .
    for (int i = 0; i < jsonGroceryItems.length(); ++i) {                         // Loop through the array of json items and convert them to grocery items.
      groceryItems.add(GroceryItem.fromJSON(jsonGroceryItems.getJSONObject(i)));  // Create the grocery item out of the json object from the array.
    }
    
    return new GroceryList(listName, groceryItems);                               // Return our new grocery list.
  }
}
