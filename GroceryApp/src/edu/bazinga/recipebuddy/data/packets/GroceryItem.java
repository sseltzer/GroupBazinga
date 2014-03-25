package edu.bazinga.recipebuddy.data.packets;

import org.json.JSONException;
import org.json.JSONObject;

public class GroceryItem {
  
  private String itemName;
  private String quantity;
  
  public GroceryItem(String itemName, String quantity) {
    this.itemName = itemName;
    this.quantity = quantity;
  }

  public String getItemName() {
    return itemName;
  }
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
  public String getQuantity() {
    return quantity;
  }
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }
  
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    
    ret.put("itemName", itemName);
    ret.put("quantity", quantity);
    
    return ret;
  }
  
  public static GroceryItem fromJSON(JSONObject jsonObject) throws JSONException {
    String itemName = jsonObject.get("itemName").toString();
    String quantity = jsonObject.get("quantity").toString();
    
    return new GroceryItem(itemName, quantity);
  }
  
  @Override
  public String toString() {
    return itemName + " : " + quantity;
  }
}
