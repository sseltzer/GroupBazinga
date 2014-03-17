package edu.bazinga.recipebuddy.data.packets;

import org.json.JSONException;
import org.json.JSONObject;

public class GroceryItem {
  
  private String itemName;
  private String quantity;
  
  public GroceryItem() {
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
    GroceryItem groceryItem = new GroceryItem();
    
    groceryItem.setItemName(jsonObject.get("itemName").toString());
    groceryItem.setQuantity(jsonObject.get("quantity").toString());
    
    return groceryItem;
  }
  
  @Override
  public String toString() {
    return itemName + " : " + quantity;
  }
}
