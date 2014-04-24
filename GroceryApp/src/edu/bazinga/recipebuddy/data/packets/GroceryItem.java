package edu.bazinga.recipebuddy.data.packets;

import org.json.JSONException;
import org.json.JSONObject;

import edu.bazinga.recipebuddy.activities.support.NameFormatter;

public class GroceryItem {
  
  private String itemName;
  private String quantity;
  private String strike;
  
  public GroceryItem(String itemName, String quantity, String strike) {
    this.itemName = NameFormatter.format(itemName);
    this.quantity = quantity;
    this.strike = strike;
  }
  public GroceryItem(String itemName, String quantity, boolean strike) {
    this.itemName = NameFormatter.format(itemName);
    this.quantity = quantity;
    setStrikeFromBoolean(strike);
  }

  public String getItemName() {
    return itemName;
  }
  public void setItemName(String itemName) {
    this.itemName = NameFormatter.format(itemName);
  }
  public String getQuantity() {
    return quantity;
  }
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }
  public boolean getStrikeAsBoolean() {
    if (strike.equals("1")) return true;
    return false;
  }
  public void setStrikeFromBoolean(boolean strike) {
    if (strike) this.strike = "1";
    this.strike = "0";
  }
  public void toggleStrike() {
    if (strike.equals("1")) strike = "0";
    else strike = "1";
  }
  
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    
    ret.put("itemName", itemName);
    ret.put("quantity", quantity);
    ret.put("strike", strike);
    
    return ret;
  }
  
  public static GroceryItem fromJSON(JSONObject jsonObject) throws JSONException {
    String itemName = jsonObject.get("itemName").toString();
    String quantity = jsonObject.get("quantity").toString();
    String strike   = jsonObject.get("strike").toString();
    
    return new GroceryItem(itemName, quantity, strike);
  }
  
  @Override
  public String toString() {
    return itemName + " : " + quantity;
  }
}
