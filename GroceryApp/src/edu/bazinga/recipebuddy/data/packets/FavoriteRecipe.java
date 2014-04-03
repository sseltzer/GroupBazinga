package edu.bazinga.recipebuddy.data.packets;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class FavoriteRecipe {

  private String id = null;
  private String name = null;
  private String imageURL = null;
  private Bitmap image = null;
  
  
  public FavoriteRecipe(String id, String name, String imageURL) {
    this.id = id;
    this.name = name;
    this.imageURL = imageURL;
  }
  
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getImageURL() {
    return imageURL;
  }
  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
  public Bitmap getImage() {
    return image;
  }
  public void setImage(Bitmap image) {
    this.image = image;
  }

  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    ret.put("id", id);
    ret.put("name", name);
    ret.put("imageURL", imageURL);   
    return ret;
  }
  public static FavoriteRecipe fromJSON(JSONObject jsonObject) throws JSONException {
    String id       = jsonObject.getString("id");
    String name     = jsonObject.getString("name"); 
    String imageURL = jsonObject.getString("imageURL");
    return new FavoriteRecipe(id, name, imageURL);
  }
}
