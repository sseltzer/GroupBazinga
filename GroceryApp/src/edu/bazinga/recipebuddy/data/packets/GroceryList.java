package edu.bazinga.recipebuddy.data.packets;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroceryList {
  private String listName = null;
  private String color = null;
  private ArrayList<GroceryItem> groceryItems = null;
  
  public GroceryList(String listName, String color) {
    this(listName, color, new ArrayList<GroceryItem>());
  }
  public GroceryList(String listName, String color, ArrayList<GroceryItem> groceryItems) {
    this.listName = listName;
    this.color = color;
    this.groceryItems = new ArrayList<GroceryItem>(groceryItems);
  }
  
  public String getListName() {
    return listName;
  }
  public void setListName(String listName) {
    this.listName = listName;
  }
  public String getColor() {
    return color;
  }
  public void setColor(String color) {
    this.color = color;
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
  public ArrayList<GroceryItem> getGroceryItems() {
    return groceryItems;
  }
  
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    
    JSONArray array = new JSONArray();
    for (int i = 0; i < groceryItems.size(); ++i) array.put(groceryItems.get(i).toJSON());
    ret.put("listName", listName);
    ret.put("color", color);
    ret.put("groceryItems", array);
    
    return ret;
  }
  
  public static GroceryList fromJSON(JSONObject jsonObject) throws JSONException {
    String listName = jsonObject.getString("listName");                           // Get the name out of the JSON object and assign our new list object it's name.
    String color = jsonObject.getString("color");
    
    ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();           // Create a new collection of our items.
    JSONArray jsonGroceryItems = jsonObject.getJSONArray("groceryItems");         // Get our array of json grocery items .
    for (int i = 0; i < jsonGroceryItems.length(); ++i) {                         // Loop through the array of json items and convert them to grocery items.
      groceryItems.add(GroceryItem.fromJSON(jsonGroceryItems.getJSONObject(i)));  // Create the grocery item out of the json object from the array.
    }
    
    return new GroceryList(listName, color, groceryItems);                        // Return our new grocery list.
  }
}
